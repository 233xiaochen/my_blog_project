package com.example.backstage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backstage.pojo.BlogCatgListVo;
import com.example.backstage.pojo.BlogCatgQueryVo;
import com.example.backstage.pojo.BlogCatgTopVo;
import com.example.backstage.pojo.BlogCatgVo;
import com.example.backstage.service.BlogCategService;
import com.example.blog.mapper.BlogCategMapper;
import com.example.blog.pojo.BlogCateg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author： chenlin
 * @create： 2024/4/26 14:01
 */
@Service
public class BlogCategServiceImpl extends ServiceImpl<BlogCategMapper, BlogCateg> implements BlogCategService {


    @Override
    public List<BlogCatgVo> dropDown() {
        List<BlogCateg> list = list(Wrappers.<BlogCateg>lambdaQuery()
                .eq(BlogCateg::getDelFlag, "0"));
        return list.stream().filter(item -> ObjectUtil.isEmpty(item.getPid()) && ObjectUtil.isEmpty(item.getTwoPid())).map(item -> {
            BlogCatgVo vo = new BlogCatgVo();
            vo.setLabel(item.getLabel());
            vo.setValue(item.getId());
            List<BlogCatgVo.ChildCatg> childrenList = list.stream().filter(it -> item.getId().equals(it.getPid())).map(it -> {
                BlogCatgVo.ChildCatg innerVo = new BlogCatgVo.ChildCatg();
                innerVo.setLabel(it.getLabel());
                innerVo.setValue(it.getId());
                return innerVo;
            }).collect(Collectors.toList());
            vo.setChildren(childrenList);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BlogCatgTopVo> getTopCatg() {
        List<BlogCateg> list = list(Wrappers.<BlogCateg>lambdaQuery()
                .eq(BlogCateg::getDelFlag, "0"));
        return list.stream().filter(item -> ObjectUtil.isEmpty(item.getPid()) && ObjectUtil.isEmpty(item.getTwoPid())).map(item -> {
            BlogCatgTopVo vo = new BlogCatgTopVo();
            vo.setBlogCatgId(item.getId());
            vo.setBlogCatgName(item.getLabel());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BlogCatgListVo> queryCatgList(BlogCatgQueryVo queryVo) {
        List<BlogCateg> list = list(Wrappers.<BlogCateg>lambdaQuery()
                .eq(BlogCateg::getDelFlag, "0")
                .like(!ObjectUtil.isEmpty(queryVo.getBlogCatgName()), BlogCateg::getLabel, queryVo.getBlogCatgName())
                .eq(!ObjectUtil.isEmpty(queryVo.getOneBlogCatgSub()), BlogCateg::getPid, queryVo.getOneBlogCatgSub())
                .eq(!ObjectUtil.isEmpty(queryVo.getTwoBlogCatgSub()), BlogCateg::getTwoPid, queryVo.getTwoBlogCatgSub()));
        List<BlogCatgListVo> collect = list.stream()
                .filter(item -> {
                    if (ObjectUtil.isEmpty(queryVo.getTwoBlogCatgSub())) {
                        return !ObjectUtil.isEmpty(item.getPid()) && ObjectUtil.isEmpty(item.getTwoPid());
                    }
                    return true;
                })
                .map(item -> {
                    BlogCatgListVo vo = new BlogCatgListVo();
                    vo.setBlogCatgId(item.getId());
                    vo.setBlogCatgName(item.getLabel());
                    if (!ObjectUtil.isEmpty(item.getPid())) {
                        vo.setSubName(getOne(Wrappers.<BlogCateg>lambdaQuery().eq(BlogCateg::getId, item.getPid())).getLabel());
                    }
                    //文章数
                    vo.setArticleCount("0");
                    vo.setIsPublic(Integer.parseInt(item.getIsPublic()));
                    return vo;
                }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public boolean updateInfo(BlogCatgQueryVo queryVo) {
        return update(Wrappers.<BlogCateg>lambdaUpdate().eq(BlogCateg::getId, queryVo.getBlogCatgId())
                .set(!ObjectUtil.isEmpty(queryVo.getBlogCatgName()), BlogCateg::getLabel, queryVo.getBlogCatgName())
                .set(!ObjectUtil.isEmpty(queryVo.getBlogCatgSub()), BlogCateg::getPid, queryVo.getBlogCatgSub())
                .set(!ObjectUtil.isEmpty(queryVo.getIsPublic()), BlogCateg::getIsPublic, queryVo.getIsPublic()));
    }

    @Override
    public String add(BlogCatgQueryVo queryVo) {
        BlogCateg vo = new BlogCateg();
        vo.setLabel(queryVo.getBlogCatgName());
        vo.setPid(queryVo.getBlogCatgSub());
        vo.setIsPublic(queryVo.getIsPublic());
        save(vo);
        return vo.getId();
    }
}
