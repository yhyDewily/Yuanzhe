package com.ruoyi.system.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author lls
 * @Date 2022-08-20 12:44
 **/
@Component
public class MyObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createTime",LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"valid",Boolean.class,true);
        this.strictInsertFill(metaObject,"expireTime",LocalDateTime.class,LocalDateTime.now().plusYears(5));
        this.strictInsertFill(metaObject,"opTime",LocalDateTime.class,LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }


}
