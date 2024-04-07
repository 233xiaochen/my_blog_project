package com.example.test.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author： chenlin
 * @create： 2024/4/8 1:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu {

    @TableField("roleId")
    private String role_id;

    @TableField("menuId")
    private String menu_id;

}
