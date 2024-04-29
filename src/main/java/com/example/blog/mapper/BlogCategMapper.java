package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.pojo.BlogCateg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author： chenlin
 * @create： 2024/4/26 14:04
 */
@Mapper
@Repository
public interface BlogCategMapper extends BaseMapper<BlogCateg> {

}
