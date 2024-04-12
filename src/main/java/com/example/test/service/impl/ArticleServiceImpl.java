package com.example.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.mapper.ArticleMapper;
import com.example.test.pojo.Article;
import com.example.test.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public void saveArticle(Article article) {
        saveOrUpdate(article);
    }

    @Override
    public Article queryArticle(String id) {
        return getById(id);
    }

    @Override
    public void delete(String id) {
        removeById(id);
    }
}
