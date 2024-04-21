package com.example.blog.web;

import com.example.blog.pojo.Article;
import com.example.blog.pojo.ResponseResult;
import com.example.blog.pojo.SearchVo;
import com.example.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return 文章实体
     */
    @GetMapping("query")
    public ResponseResult<Article> query(String bamId) {
        Article article = articleService.queryArticle(bamId);
        return new ResponseResult<>(200,article);
    }

    @PostMapping("delete")
    public ResponseResult<Boolean> delete(String id) {
        articleService.delete(id);
        return new ResponseResult<>(200,"删除文章成功！");
    }

    @GetMapping("dataStatistics")
    public ResponseResult<Map<String,Object>> dataStatistics(){
        Map<String,Object> map = new HashMap<>();
        String articleNumber = articleService.queryCountBySql();
        //文章总数
        map.put("articleNumber",articleNumber);
        //TODO
        //留言条数
        map.put("commentNumber","23");
        return new ResponseResult<>(200,map);
    }

    @GetMapping("articleRanking")
    public ResponseResult<List<Article>> articleRanking(String blogCatgId){
        List<Article> articleList = articleService.queryArticleTopTen(blogCatgId);
        return new ResponseResult<>(200,articleList);
    }

    /**
     * 文章列表
     * @return 文章列表
     */
    @PostMapping("queryList")
    public ResponseResult<Map<String,Object>> queryList(@RequestBody SearchVo searchVo) {
        Map<String,Object> map = new HashMap<>();
        long startTime = System.currentTimeMillis(); // 记录发送请求的时间
        List<Article> articleList = articleService.queryList(searchVo.getSelectName(), searchVo.getPage(),searchVo.getPageSize(),searchVo.getType());
        long count = articleService.queryArticleCount(searchVo.getSelectName(),searchVo.getType());
        long endTime = System.currentTimeMillis(); // 记录收到响应的时间
        long responseTimeMillis = endTime - startTime; // 计算响应时间（毫秒）
        // 计算响应时间（秒）
        double responseTimeSeconds = responseTimeMillis / 1000.0;
        map.put("data",articleList);
        map.put("total",String.valueOf(count));
        map.put("searchTime",responseTimeSeconds+"s");
        return new ResponseResult<>(200,map);
    }
}
