package com.example.test.pojo;

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
    //标识
    @TableId
    private String id;
    //标题
    @TableField("title")
    private String title;
    //内容
    @TableField("content")
    private String content;
    //收藏量
    @TableField("collect")
    private int collect;
    //点赞量
    @TableField("like_count")
    private int likeCount;
    //不喜欢量
    @TableField("dislike_count")
    private int dislikeCount;
    //评论数量
    @TableField("comment_volume")
    private int commentVolume;
    //浏览量
    @TableField("view_volume")
    private int viewVolume;
    //用户
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    //更新人
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
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
