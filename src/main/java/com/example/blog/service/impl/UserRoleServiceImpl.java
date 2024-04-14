package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.mapper.UserRoleMapper;
import com.example.blog.pojo.UserRole;
import com.example.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/8 1:13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


    @Override
    public void bindRole(String userId,String[] ids) {
        List<UserRole> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            UserRole role = new UserRole();
            role.setUser_id(userId);
            role.setUser_id(ids[i]);
            list.add(role);
        }
        saveBatch(list);
    }
}
