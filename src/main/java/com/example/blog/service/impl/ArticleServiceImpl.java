package com.example.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.pojo.Article;
import com.example.blog.service.ArticleService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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


    private static final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(10);


    @Override
    public String saveArticle(Article article) {
        if (Objects.isNull(article.getId())) {
            save(article);
            //保存文档到es
            IndexRequest indexRequest = new IndexRequest("article");
            indexRequest.id(article.getId());
            indexRequest.source(JSONUtil.toJsonStr(article), XContentType.JSON);
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
        GetRequest getRequest = new GetRequest("article", id);
        Article article = null;
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            article = JSONUtil.toBean(getResponse.getSourceAsString(), Article.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        //增加文章浏览量

        return article;
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
    public List<Article> queryList(String keyWorld, String pageNumber, String pageSize) {
        SearchRequest searchRequest = new SearchRequest("article");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false)
                .field("title").field("content")
                .preTags("<span style = 'color:red;'>")
                .postTags("</span>");

        sourceBuilder
                .query(!StrUtil.isEmpty(keyWorld) ? QueryBuilders.multiMatchQuery(keyWorld, "title", "content") : QueryBuilders.matchAllQuery())
                .from((Integer.parseInt(pageNumber)-1)*Integer.parseInt(pageSize)).size(Integer.parseInt(pageSize))
                .sort("update_time", SortOrder.DESC)
                .fetchSource(new String[]{}, new String[]{})
                .highlighter(highlightBuilder)
                .postFilter(QueryBuilders.existsQuery("content"));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
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
            highlightFields.forEach((k, v) -> {
                if ("title".equals(k)) {
                    article.setTitle(v.fragments()[0].toString());
                } else if ("content".equals(k)) {
                    article.setCollect(v.fragments()[0].toString());
                }
            });
            articleList.add(article);
        }
        return articleList;
    }
}
