package com.tomhusky.wechatmvc.server.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @Author: lwj
 * @Package: com.meishang.wxcp.common.base
 * @interfaceName: BaseDao
 * @CreateDate: 2021/7/16 15:04
 * @Description: 加强mapper
 */
public interface BaseDao<T> extends BaseMapper<T> {

    /**
     * 批量插入（mysql）
     *
     * @param entityList 实体类对象集合
     * @return 受影响的行数
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);

}
