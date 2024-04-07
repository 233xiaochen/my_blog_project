package com.example.test.web;

import com.example.test.pojo.ResponseResult;
import com.example.test.pojo.Role;
import com.example.test.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/7 10:23
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("query")
    public ResponseResult<List<Role>> query(Role role) {
        List<Role> roleList = roleService.query(role);
        return new ResponseResult<>(200,roleList);
    }

    @PostMapping("addOrUpdate")
    public ResponseResult<Long> addOrUpdate(Role role) {
        long id = roleService.addOrUpdate(role);
        return new ResponseResult<>(200,id);
    }

    @GetMapping("delete")
    public ResponseResult<Long> delete(String id) {
        roleService.delete(id);
        return new ResponseResult<>(200,"删除角色成功");
    }

}
