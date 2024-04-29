package com.example.backstage.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/4/26 9:38
 */
@Data
public class BlogCatgVo {

    //分类标识
    private String value;
    //分类名称
    private String label;
    //子分类
    private List<ChildCatg> children;

    @Data
    public static class ChildCatg{
        //分类标识
        private String value;
        //分类名称
        private String label;
    }

}
