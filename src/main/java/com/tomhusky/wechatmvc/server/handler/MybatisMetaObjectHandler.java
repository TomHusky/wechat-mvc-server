package com.tomhusky.wechatmvc.server.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: lwj
 * @Package: com.meishang.wxcp.common.handler
 * @ClassName: MybatisMetaObjectHandler
 * @CreateDate: 2021/6/3 16:45
 * @Description: mybatis 日期自动填充处理器
 */
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime dateTime = LocalDateTime.now();
        this.setFieldValByName("createTime", dateTime, metaObject);
        this.setFieldValByName("updateTime", dateTime, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
