package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.pojo.Article;
import com.example.blog.pojo.UserRole;

/**
 * @author： chenlin
 * @create： 2024/4/8 1:12
 */
public interface UserRoleService extends IService<UserRole> {

    void bindRole(String userId, String[] ids);
}
