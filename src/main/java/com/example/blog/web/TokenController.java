package com.example.blog.web;

import com.example.blog.pojo.ResponseResult;
import com.example.blog.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/1/24 15:32
 */
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("refresh")
    public ResponseResult<Map<String,Object>> refresh(String token){
        return tokenService.refresh(token);
    }

}
