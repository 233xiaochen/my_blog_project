package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author： chenlin
 * @create： 2024/4/7 10:48
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}
