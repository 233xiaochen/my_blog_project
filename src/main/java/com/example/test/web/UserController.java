package com.example.test.web;

import com.example.test.pojo.ResponseResult;
import com.example.test.pojo.User;
import com.example.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userServcie;

    /**
     * 用户登录
     *
     * @param user username、password
     * @return 登录信息
     */
    @PostMapping("login")
    public ResponseResult<Map<String, String>> login(@RequestBody User user) {
        return userServcie.login(user);
    }

    /**
     * 用户注销
     *
     * @return 注销信息
     */
    @PostMapping("loginOut")
    public ResponseResult<Map<String,String>> loginOut() {
        return userServcie.loginOut();
    }

    /**
     * 用户注册
     *
     * @return 注册信息
     */
    @PostMapping("register")
    public ResponseResult<Map<String,String>> register(User user) {
        return userServcie.register(user);
    }

//    @PostMapping("register")
//    public ResponseResult<Map<String,String>> register(User user) {
//        return userServcie.register(user);
//    }
}
