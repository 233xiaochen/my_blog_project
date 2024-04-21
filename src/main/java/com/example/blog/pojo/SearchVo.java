package com.example.blog.pojo;

import lombok.Data;

/**
 * @author： chenlin
 * @create： 2024/4/20 23:11
 */
@Data
public class SearchVo {
    private String page;
    private String pageSize;
    private String selectName;
    private String type;
}
