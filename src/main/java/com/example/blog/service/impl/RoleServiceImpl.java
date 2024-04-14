package com.example.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.mapper.RoleMapper;
import com.example.blog.pojo.Role;
import com.example.blog.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/7 10:46
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Override
    public List<Role> query(Role role) {
        List<Role> list = list(Wrappers.<Role>lambdaQuery().eq(!ObjectUtil.isEmpty(role.getName()), Role::getName, role.getName()));
        return list;
    }

    @Override
    public long addOrUpdate(Role role) {
        saveOrUpdate(role);
        return role.getId();
    }

    @Override
    public void delete(String id) {
        removeById(id);
    }
}
