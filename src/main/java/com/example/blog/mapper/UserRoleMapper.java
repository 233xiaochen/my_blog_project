package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author： chenlin
 * @create： 2024/4/8 1:14
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {



}
