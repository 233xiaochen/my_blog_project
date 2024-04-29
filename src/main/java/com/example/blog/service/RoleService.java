package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.pojo.Role;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/7 10:45
 */

public interface RoleService extends IService<Role> {

    //查询角色
    List<Role> query(Role role);
    //新增更新角色
    long addOrUpdate(Role role);
    //删除角色
    void delete(String id);
}
