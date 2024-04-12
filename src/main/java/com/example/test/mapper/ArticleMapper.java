package com.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:13
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    


}
