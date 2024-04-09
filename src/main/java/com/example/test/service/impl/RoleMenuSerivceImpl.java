package com.example.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.mapper.RoleMenuMapper;
import com.example.test.pojo.RoleMenu;
import com.example.test.pojo.UserRole;
import com.example.test.service.RoleMenuSerivce;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/8 1:38
 */
@Service
public class RoleMenuSerivceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuSerivce {

    @Override
    public void bindMenu(String roleId, String[] ids) {
        List<RoleMenu> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            RoleMenu role = new RoleMenu();
            role.setRole_id(roleId);
            role.setMenu_id(ids[i]);
            list.add(role);
        }
        saveBatch(list);
    }
}
