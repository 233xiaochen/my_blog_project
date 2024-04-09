package com.example.test.service;

import com.example.test.pojo.ResponseResult;
import com.example.test.pojo.User;

import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/3/4 14:42
 */
public interface UserService {
    //用户登录
    ResponseResult<Map<String,String>> login(User user);
    //用户注销
    ResponseResult<Map<String,String>> loginOut();
    //用户注册
    ResponseResult<Map<String,String>> register(User user);
    //账号停用
    boolean deactivateAccount(long id);
    //账号启用
    boolean enableAccount(long id);

    //用户绑定部门

    //部门绑定角色


}
