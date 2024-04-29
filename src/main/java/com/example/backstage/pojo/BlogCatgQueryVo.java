package com.example.backstage.pojo;

import lombok.Data;

/**
 * @author： chenlin
 * @create： 2024/4/27 16:58
 */
@Data
public class BlogCatgQueryVo {

    private String bamTitle;     // 分类id
    private String blogCatgId;     // 分类id
    private String blogCatgTwoId;     // 分类id
    private String blogCatgName;    // 分类名称
    private String blogCatgSub;     // 文章分类上级
    private String blogCatgSubName; // 文章分类上级名
    private String isPublic;
    private String oneBlogCatgSub;
    private String twoBlogCatgSub;
    private String page;

}
