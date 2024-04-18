package com.example.blog.web;

import com.example.blog.pojo.Article;
import com.example.blog.pojo.ResponseResult;
import com.example.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章实体
     */
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

    /**
     * 文章列表
     * @param keyWorld 关键词
     * @param pageNumber 1
     * @param pageSize 10
     * @return 文章列表
     */
    @PostMapping("queryList")
    public ResponseResult<List<Article>> queryList(String keyWorld,String pageNumber,String pageSize) {
        List<Article> list = articleService.queryList(keyWorld,pageNumber,pageSize);
        return new ResponseResult<>(200,list);
    }
}
