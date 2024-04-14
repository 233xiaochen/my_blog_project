package com.example.blog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： chenlin
 * @create： 2024/3/18 1:44
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test")
    @PreAuthorize("hasAuthority('system:dept:test')")
    public String test(){
        return "hello world";
    }

}
