package com.example.blog.web;

import com.example.blog.pojo.ResponseResult;
import com.example.blog.pojo.User;
import com.example.blog.service.RoleMenuSerivce;
import com.example.blog.service.UserRoleService;
import com.example.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userServcie;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuSerivce roleMenuSerivce;
    /**
     * 用户登录
     * @param user username、password
     * @return 登录信息
     */
    @PostMapping("login")
    public ResponseResult<Map<String, String>> login(@RequestBody User user) {
        return userServcie.login(user);
    }

    /**
     * 用户注销
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

    @PostMapping("userBindRole")
    public ResponseResult<Long> bindRole(String userId,String[] ids) {
        userRoleService.bindRole(userId,ids);
        return new ResponseResult<>(200,"绑定角色成功");
    }

    @PostMapping("roleBindMenu")
    public ResponseResult<Long> roleBindMenu(String roleId,String[] ids) {
        roleMenuSerivce.bindMenu(roleId,ids);
        return new ResponseResult<>(200,"绑定菜单成功");
    }

    @PostMapping("getMenuIdsByRole")
    public ResponseResult<List<String>> getMenuIdsByRole(String roleId) {
        List<String> menuIdsByRole = roleMenuSerivce.getMenuIdsByRole(roleId);
        return new ResponseResult<>(200,menuIdsByRole);
    }
}
