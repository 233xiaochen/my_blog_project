package com.example.blog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/3/18 1:44
 */
@Slf4j
@RestController
@RequestMapping
public class TestController {

    @GetMapping("/notice/frontList")
    public Map<String,Object> test11(){
        Map<String,Object> map = new HashMap<>();
        map.put("length",9);
        return map;
    }
    @GetMapping("/blogCatg/classBar")
    public String tdddest(){
        return null;
    }
    @PostMapping("/blogArticle/frontList")
    public String tessafdt(){
        return null;
    }
    @GetMapping("/blogArticle/readTop")
    public String asssdasf(){
        return null;
    }
    @GetMapping("/rotationChart/list")
    public String tdfghdghest(){
        return null;
    }
    @GetMapping("/myFriend/homePageList")
    public String testadsgsdf(){
        return null;
    }
    @GetMapping("/jrebel/leases")
    public String sdfadsfgerrgg(){
        return "hello world";
    }

    @GetMapping("/basic/site/front")
    public Map<String, Object> sdfsdfsdfadsfgerrgg(){
        Map<String,Object> map = new HashMap<>();
        map.put("articleNumber","9");
        map.put("commentNumber","23");
        return map;
    }
}
