package com.example.blog.service.impl;

import com.example.blog.pojo.ResponseResult;
import com.example.blog.service.TokenService;
import com.example.blog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/3/12 9:51
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Override
    public ResponseResult<Map<String, Object>> refresh(String token) {
        try {
            Map<String, Object> map = new HashMap<>();
            Claims claims = JwtUtil.parseJWT(token);
            String userId = claims.getSubject();
            String accessToken = JwtUtil.createJWT(userId, 30 * 60 * 1000L);
            String refreshToken = JwtUtil.createJWT(userId, 7 * 24 * 60 * 60 * 1000L);
            map.put("accessToken", accessToken);
            map.put("refreshToken", refreshToken);
            return new ResponseResult<>(200, map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        }
    }
}
