package com.example.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backstage.pojo.BlogCatgQueryVo;
import com.example.blog.pojo.Article;

import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:10
 */
public interface BackArticleService extends IService<Article>  {

    Map<String,Object> queryList(BlogCatgQueryVo queryVo);
}
