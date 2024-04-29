package com.example.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backstage.pojo.BlogCatgListVo;
import com.example.backstage.pojo.BlogCatgQueryVo;
import com.example.backstage.pojo.BlogCatgTopVo;
import com.example.backstage.pojo.BlogCatgVo;
import com.example.blog.pojo.BlogCateg;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/26 14:01
 */
public interface BlogCategService extends IService<BlogCateg> {
    List<BlogCatgVo> dropDown();

    List<BlogCatgTopVo> getTopCatg();
    List<BlogCatgListVo> queryCatgList(BlogCatgQueryVo queryVo);

    boolean updateInfo(BlogCatgQueryVo queryVo);
    String add(BlogCatgQueryVo queryVo);
}
