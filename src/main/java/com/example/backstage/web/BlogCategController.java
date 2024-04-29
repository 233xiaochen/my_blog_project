package com.example.backstage.web;

import com.example.backstage.pojo.BlogCatgListVo;
import com.example.backstage.pojo.BlogCatgQueryVo;
import com.example.backstage.pojo.BlogCatgTopVo;
import com.example.backstage.pojo.BlogCatgVo;
import com.example.backstage.service.BlogCategService;
import com.example.blog.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/26 14:06
 */
@Slf4j
@RestController
@RequestMapping("blogCateg")
public class BlogCategController {

    @Autowired
    private BlogCategService blogCategService;


    @GetMapping("addArticle/dropDown")
    public ResponseResult<List<BlogCatgVo>> dropDown() {
       List<BlogCatgVo> list = blogCategService.dropDown();
       return new ResponseResult<>(HttpStatus.OK.value(), list);
    }

    @GetMapping("getTopCatg")
    public ResponseResult<List<BlogCatgTopVo>> getTopCatg() {
        List<BlogCatgTopVo> list = blogCategService.getTopCatg();
        return new ResponseResult<>(HttpStatus.OK.value(), list);
    }

    @PostMapping("list")
    public ResponseResult<List<BlogCatgListVo>> asdasd(@RequestBody BlogCatgQueryVo queryVo) {
        List<BlogCatgListVo> list = blogCategService.queryCatgList(queryVo);
        return new ResponseResult<>(HttpStatus.OK.value(), list);
    }

    @PostMapping("update")
    public ResponseResult<Boolean> updateInfo(@RequestBody BlogCatgQueryVo queryVo) {
        return new ResponseResult<>(HttpStatus.OK.value(),blogCategService.updateInfo(queryVo));
    }
    @PostMapping("add")
    public ResponseResult<String> add(@RequestBody BlogCatgQueryVo queryVo) {
        return new ResponseResult<>(HttpStatus.OK.value(),blogCategService.add(queryVo));
    }

}
