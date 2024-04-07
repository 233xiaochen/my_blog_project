package com.example.test.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.mapper.MenuMapper;
import com.example.test.pojo.Menu;
import com.example.test.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/7 11:23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Override
    public void delete(String id) {
        removeById(id);
    }

    @Override
    public long addOrUpdate(Menu menu) {
        saveOrUpdate(menu);
        return menu.getId();
    }

    @Override
    public List<Menu> query(Menu menu) {
        return list(Wrappers.<Menu>lambdaQuery().like(!ObjectUtil.isEmpty(menu.getMenuName()), Menu::getMenuName, menu.getMenuName()));
    }
}
