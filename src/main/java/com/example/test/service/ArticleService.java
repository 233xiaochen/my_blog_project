package com.example.test.service;

import com.example.test.pojo.Article;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:10
 */
public interface ArticleService {
    
    void saveArticle(Article article);

    Article queryArticle(String id);

    void delete(String id);
}
