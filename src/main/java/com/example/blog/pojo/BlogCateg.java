package com.example.blog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author： chenlin
 * @create： 2024/4/26 9:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_categ")
public class BlogCateg {

    //分类标识
    @TableId
    private String id;
    //分类名
    @TableField("label")
    private String label;
    //父id
    @TableField("pid")
    private String pid;
    //二级父id
    @TableField("two_pid")
    private String twoPid;
    //是否公开
    @TableField("is_public")
    private String isPublic;


    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @OrderBy
    private Date updateTime;
    /**
     * 是否删除（0未删除 1已删除）
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

}
