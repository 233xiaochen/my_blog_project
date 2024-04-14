package com.example.test.web;

import com.example.test.pojo.Article;
import com.example.test.pojo.ResponseResult;
import com.example.test.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： chenlin
 * @create： 2024/4/9 13:48
 */
@Slf4j
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("save")
    public ResponseResult<String> addOrUpdate(Article article) {
        String articleId = articleService.saveArticle(article);
        return new ResponseResult<>(200,"保存文章成功！",articleId);
    }

    @GetMapping("query")
    public ResponseResult<Article> query(String id) {
        Article article = articleService.queryArticle(id);
        return new ResponseResult<>(200,article);
    }

    @PostMapping("delete")
    public ResponseResult<Boolean> delete(String id) {
        articleService.delete(id);
        return new ResponseResult<>(200,"删除文章成功！");
    }
}
