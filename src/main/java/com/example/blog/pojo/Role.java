package com.example.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author： chenlin
 * @create： 2024/4/7 10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -54979041104113740L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 角色名称
     */
    @TableField("name")
    private String name;
    /**
     * 角色权限字符串
     */
    @TableField("role_key")
    private String roleKey;
    /**
     * 角色状态（0正常 1停用）
     */
    @TableField("status")
    private String status;
    /**
     * 创建人的用户id
     */
    @TableField("create_by")
    private Long createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 备注
     */
    @TableField("remark")
    private Integer remark;

}
