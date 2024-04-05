package com.example.test.service;

import com.example.test.pojo.ResponseResult;

import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/3/12 9:50
 */
public interface TokenService {
    ResponseResult<Map<String,Object>> refresh(String token);
}
