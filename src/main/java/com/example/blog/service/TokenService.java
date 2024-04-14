package com.example.blog.service;

import com.example.blog.pojo.ResponseResult;

import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/3/12 9:50
 */
public interface TokenService {
    ResponseResult<Map<String,Object>> refresh(String token);
}
