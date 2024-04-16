package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.pojo.Article;
import com.example.blog.pojo.RoleMenu;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/8 1:37
 */
public interface RoleMenuSerivce extends IService<RoleMenu> {

    void bindMenu(String roleId, String[] ids);

    List<String> getMenuIdsByRole(String roleId);
}
