package com.example.test.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.mapper.ArticleMapper;
import com.example.test.pojo.Article;
import com.example.test.service.ArticleService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
                    System.out.println("失败次数");
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
                    System.out.println("失败次数");
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
        GetRequest getRequest = new GetRequest("article",id);
        Article article = null;
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            article = JSONUtil.toBean(getResponse.getSourceAsString(), Article.class);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return article;
    }

    @Override
    public void delete(String id) {
        removeById(id);
        DeleteRequest deleteRequest = new DeleteRequest("article",id);
        AtomicReference<ScheduledFuture<?>> future = new AtomicReference<>();
        future.set(scheduler.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("失败次数");
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
}
