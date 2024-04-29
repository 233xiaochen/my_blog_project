package com.example.backstage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backstage.pojo.BlogCatgQueryVo;
import com.example.backstage.service.BackArticleService;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： chenlin
 * @create： 2024/4/12 1:12
 */
@Service
public class BackArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements BackArticleService {


    @Override
    public Map<String,Object> queryList(BlogCatgQueryVo queryVo) {
        Map<String,Object> map = new HashMap<>();
        List<Article> list = list(Wrappers.<Article>lambdaQuery()
                .eq(Article::getDelFlag, "0")
                .like(ObjectUtil.isNotEmpty(queryVo.getBamTitle()), Article::getTitle, queryVo.getBamTitle())
                .eq(ObjectUtil.isNotEmpty(queryVo.getBlogCatgId()), Article::getCatgId, queryVo.getBlogCatgId())
                .eq(ObjectUtil.isNotEmpty(queryVo.getBlogCatgTwoId()), Article::getBlogTwoId, queryVo.getBlogCatgTwoId()));
        map.put("data",list);
        map.put("total",list.size());
        return map;
    }
}
