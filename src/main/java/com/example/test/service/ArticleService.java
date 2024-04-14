package com.example.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.test.pojo.Article;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:10
 */
public interface ArticleService extends IService<Article>  {

    String saveArticle(Article article);

    Article queryArticle(String id);

    void delete(String id);
}
