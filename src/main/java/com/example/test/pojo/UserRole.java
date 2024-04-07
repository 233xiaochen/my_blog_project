package com.example.test.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author： chenlin
 * @create： 2024/4/7 18:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole {

    @TableField("userId")
    private String user_id;

    @TableField("roleId")
    private String role_id;

}
