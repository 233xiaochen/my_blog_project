package com.example.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.config.RedisCache;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.pojo.Article;
import com.example.blog.service.ArticleService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private RedisCache redisCache;


    private static final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(10);


    @Override
    public String saveArticle(Article article) {
        if (Objects.isNull(article.getId())) {
            save(article);
            //保存文档到es
            IndexRequest indexRequest = new IndexRequest("article");
            indexRequest.id(article.getId());
            JSONObject object = JSONObject.parseObject(JSONUtil.toJsonStr(article));
            object.remove("id");
            object.remove("content");
            object.remove("delFlag");
            indexRequest.source(JSONUtil.toJsonStr(object), XContentType.JSON);
            AtomicReference<ScheduledFuture<?>> future = new AtomicReference<>();
            future.set(scheduler.scheduleWithFixedDelay(() -> {
                try {
                    IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                    if (!Objects.isNull(index)) {
                        // 取消定时任务
                        future.get().cancel(false);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }, 0, 8, TimeUnit.SECONDS));
            //添加排行榜
            Map<String,Object> map = new HashMap<>();
            map.put("id",article.getId());
            map.put("title", article.getTitle());
            redisCache.addLeaderboard("articleRanking",JSONUtil.toJsonStr(map));
        } else {
            updateById(article);
            UpdateRequest updateRequest = new UpdateRequest("article", article.getId());
            updateRequest.doc(JSONUtil.toJsonStr(article), XContentType.JSON);
            AtomicReference<ScheduledFuture<?>> future = new AtomicReference<>();
            future.set(scheduler.scheduleWithFixedDelay(() -> {
                try {
                    UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
                    if (!Objects.isNull(update)) {
                        // 取消定时任务
                        future.get().cancel(false);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }, 0, 8, TimeUnit.SECONDS));
        }
        return article.getId();
    }

    @Override
    public Article queryArticle(String id) {
        Article byId = getById(id);
        //增加文章浏览量
        Map<String,Object> map = new HashMap<>();
        map.put("id",byId.getId());
        map.put("title", byId.getTitle());
        redisCache.addLeaderboard("articleRanking",JSONUtil.toJsonStr(map));
        return byId;
    }

    @Override
    public void delete(String id) {
        removeById(id);
        DeleteRequest deleteRequest = new DeleteRequest("article", id);
        AtomicReference<ScheduledFuture<?>> future = new AtomicReference<>();
        future.set(scheduler.scheduleWithFixedDelay(() -> {
            try {
                DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
                if (!Objects.isNull(delete)) {
                    // 取消定时任务
                    future.get().cancel(false);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }, 0, 8, TimeUnit.SECONDS));
    }

    @Override
    public List<Article> queryList(String keyWorld, String pageNumber, String pageSize,String type) {
        SearchRequest searchRequest = new SearchRequest("article");
        SearchResponse searchResponse = null;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        if ("1".equals(type) || null == type){
            highlightBuilder.requireFieldMatch(false)
                    .field("title").field("description")
                    .preTags("<span style = 'color:red;'>")
                    .postTags("</span>");
            sourceBuilder
                    .query(!StrUtil.isEmpty(keyWorld) ? QueryBuilders.multiMatchQuery(keyWorld, "title", "description") : QueryBuilders.matchAllQuery())
                    .from((Integer.parseInt(pageNumber)-1)*Integer.parseInt(pageSize)).size(Integer.parseInt(pageSize))
                    .sort("updateTime", SortOrder.DESC)
                    .fetchSource(new String[]{}, new String[]{})
                    .highlighter(highlightBuilder)
                    .postFilter(QueryBuilders.existsQuery("description"));
            searchRequest.source(sourceBuilder);
        }else if ("2".equals(type)){
            sourceBuilder
                    .query(QueryBuilders.multiMatchQuery(keyWorld, "blogTwoId","blogThreeId"))
                    .from((Integer.parseInt(pageNumber)-1)*Integer.parseInt(pageSize)).size(Integer.parseInt(pageSize))
                    .sort("updateTime", SortOrder.DESC)
                    .fetchSource(new String[]{}, new String[]{});
            searchRequest.source(sourceBuilder);
        }
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        List<Article> articleList = new ArrayList<>();
        if (Objects.isNull(searchResponse)) {
            return articleList;
        }
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Article article = JSONUtil.toBean(hit.getSourceAsString(), Article.class);
            article.setId(hit.getId());
            highlightFields.forEach((k, v) -> {
                if ("title".equals(k)) {
                    article.setTitle(v.fragments()[0].toString());
                } else if ("description".equals(k)) {
                    article.setDescription(v.fragments()[0].toString());
                }
            });
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public long queryArticleCount(String keyWorld,String type) {
        SearchRequest searchRequest = new SearchRequest("article");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchResponse searchResponse = null;
        if ("1".equals(type) || null == type) {
            sourceBuilder
                    .query(!StrUtil.isEmpty(keyWorld) ? QueryBuilders.multiMatchQuery(keyWorld, "title", "description") : QueryBuilders.matchAllQuery());
        }else if ("2".equals(type)){
            sourceBuilder
                    .query(QueryBuilders.multiMatchQuery(keyWorld, "blogTwoId", "blogThreeId"));
        }
        // 添加一个count聚合
        sourceBuilder.aggregation(AggregationBuilders.count("total_count").field("_id"));
        searchRequest.source(sourceBuilder);
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        if (Objects.isNull(searchResponse)) {
            return 0;
        }
        // 获取count聚合的结果
        Aggregations aggregations = searchResponse.getAggregations();
        ValueCount totalCount = aggregations.get("total_count");
        long totalHits = totalCount.getValue();
        return totalHits;
    }

    @Override
    public List<Article> queryArticleTopTen(String blogCatgId) {
        Set<String> set = redisCache.getLeaderboard("articleRanking", 10);
        List<Article> list = new ArrayList<>();
        set.forEach(item -> {
            Article bean = JSONUtil.toBean(item, Article.class);
            list.add(bean);
        });
        return list;
    }

    @Override
    public String queryCountBySql() {
        long count = count(Wrappers.<Article>lambdaQuery()
                .eq(Article::getDelFlag, "0"));
        return String.valueOf(count);
    }
}
