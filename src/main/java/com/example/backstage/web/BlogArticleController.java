package com.example.backstage.web;

import com.example.backstage.pojo.BlogCatgQueryVo;
import com.example.backstage.service.BackArticleService;
import com.example.blog.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/4/26 9:23
 */
@Slf4j
@RestController
@RequestMapping("blogArticle")
public class BlogArticleController {

    @Autowired
    private BackArticleService backArticleService;

    @PostMapping("backList")
    public ResponseResult<Map<String,Object>> addOrUpdate(@RequestBody BlogCatgQueryVo queryVo) {
        return new ResponseResult<>(200,backArticleService.queryList(queryVo));
    }

}
