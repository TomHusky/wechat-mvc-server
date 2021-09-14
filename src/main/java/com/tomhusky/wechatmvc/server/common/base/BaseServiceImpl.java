package com.tomhusky.wechatmvc.server.common.base;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: lwj
 * @Package: com.meishang.wxcp.common.base.impl
 * @ClassName: BaseServiceImpl
 * @CreateDate: 2021/7/16 15:23
 * @Description: 基础服务增强实现类
 */
public class BaseServiceImpl<M extends BaseDao<T>, T> extends ServiceImpl<M, T> {

    @Autowired
    protected M dao;

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        int size = 0;
        List<T> allList = new ArrayList<>(entityList);
        if (entityList.size() > DEFAULT_BATCH_SIZE) {
            List<List<T>> splitList = CollectionUtil.split(allList, DEFAULT_BATCH_SIZE);
            for (List<T> list : splitList) {
                size += dao.insertBatchSomeColumn(list);
            }
        } else {
            size = dao.insertBatchSomeColumn(entityList);
        }
        return size == entityList.size();
    }
}
