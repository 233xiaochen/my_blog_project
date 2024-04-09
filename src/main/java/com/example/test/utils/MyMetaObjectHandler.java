package com.example.test.utils;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.test.service.impl.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import static com.example.test.utils.UserUtils.getCurUserAccount;

/**
 * 新增/更新实体时插入时间
 *
 * @author zengjh
 * @since 2022-02-21
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {



    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("createTime", DateUtil.date(), metaObject);
        this.setFieldValByName("updateTime", DateUtil.date(), metaObject);
        LoginUser curUserAccount = getCurUserAccount();
        this.strictInsertFill(metaObject, "createBy", String.class, String.valueOf(curUserAccount.getUser().getId()));
        this.strictInsertFill(metaObject, "updateBy", String.class, String.valueOf(curUserAccount.getUser().getId()));
        this.setFieldValByName("delFlag", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        LoginUser curUserAccount = getCurUserAccount();
        this.setFieldValByName("updateTime", DateUtil.date(), metaObject);
        this.strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(curUserAccount.getUser().getId()));
    }
}
