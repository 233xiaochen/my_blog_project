package com.example.blog.service;

import com.example.blog.pojo.Menu;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/7 11:20
 */
public interface MenuService {

    //删除菜单
    void delete(String id);
    //新增修改菜单
    long addOrUpdate(Menu menu);
    //查询菜单
    List<Menu> query(Menu menu);

}
