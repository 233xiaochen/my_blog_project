package com.example.blog.web;

import com.example.blog.pojo.Menu;
import com.example.blog.pojo.ResponseResult;
import com.example.blog.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/7 11:19
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("query")
    public ResponseResult<List<Menu>> query(Menu menu) {
        List<Menu> menuList = menuService.query(menu);
        return new ResponseResult<>(200,menuList);
    }

    @PostMapping("addOrUpdate")
    public ResponseResult<Long> addOrUpdate(Menu menu) {
        long id = menuService.addOrUpdate(menu);
        return new ResponseResult<>(200,id);
    }

    @GetMapping("delete")
    public ResponseResult<Long> delete(String id) {
        menuService.delete(id);
        return new ResponseResult<>(200,"删除角色成功");
    }

}
