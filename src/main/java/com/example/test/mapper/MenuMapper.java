package com.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : k
 * @Date : 2022/3/24
 * @Desc :
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {


    List<String> selectPermsByUserId(Long userid);


}
