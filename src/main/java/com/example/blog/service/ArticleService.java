package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.pojo.Article;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:10
 */
public interface ArticleService extends IService<Article>  {

    String saveArticle(Article article);

    Article queryArticle(String id);

    void delete(String id);

    List<Article> queryList(String keyWorld,String pageNumber,String pageSize);
}
