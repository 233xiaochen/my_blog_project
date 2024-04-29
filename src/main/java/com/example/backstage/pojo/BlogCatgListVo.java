package com.example.backstage.pojo;

import lombok.Data;

/**
 * @author： chenlin
 * @create： 2024/4/27 16:20
 */
@Data
public class BlogCatgListVo {

    //分类标识
    private String blogCatgId;
    //分类名
    private String blogCatgName;
    //上级分类
    private String subName;
    //文章数
    private String articleCount;
    //是否公开
    private int isPublic;

}
