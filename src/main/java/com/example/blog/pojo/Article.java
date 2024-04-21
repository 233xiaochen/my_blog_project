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

import java.io.Serializable;
import java.util.Date;

/**
 * @author： chenlin
 * @create： 2024/4/9 13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article implements Serializable {
    private static final long serialVersionUID = -54979041104115340L;
    //标识
    @TableId
    private String id;
    //标题
    @TableField("title")
    private String title;
    //文章描述
    @TableField("description")
    private String description;
    //内容
    @TableField("content")
    private String content;
    //图片地址
    @TableField("img_src")
    private String imgSrc;
    //文章类型
    @TableField("type")
    private String type;
    //文章类型名称
    @TableField("type_name")
    private String typeName;
    //是否置顶 1制定
    @TableField("is_top")
    private String isTop;
    //博客二级分类标识
    @TableField("blog_two_id")
    private String blogTwoId;
    //博客三级分类标识
    @TableField("blog_three_id")
    private String blogThreeId;
    //博客分类名称
    @TableField("catg_name")
    private String catgName;
    //收藏量
    @TableField("collect")
    private String collect;
    //点赞量
    @TableField("like_count")
    private String likeCount;
    //评论数量
    @TableField("comment_volume")
    private String commentVolume;
    //浏览量
    @TableField("view_volume")
    private String viewVolume;
    //用户
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;
    //创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    //更新人
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    //更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @OrderBy
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private String delFlag;
}
