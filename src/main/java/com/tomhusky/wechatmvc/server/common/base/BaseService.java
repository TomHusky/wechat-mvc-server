package com.tomhusky.wechatmvc.server.common.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tomhusky.wechatmvc.server.common.PageList;
import com.tomhusky.wechatmvc.server.common.PageVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: lwj
 * @Package: com.meishang.wxcp.common.base
 * @interfaceName: BaseServer
 * @CreateDate: 2021/7/16 15:20
 * @Description: 基础服务增强
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 分页查询 返回实体对象
     *
     * @param pageVO: 分页参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     */
    PageList<T> queryPage(PageVo pageVO);


    /**
     * 分页查询,返回指定VO
     *
     * @param pageVO: 分页参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     */
    PageList<?> queryPageVo(PageVo pageVO);

    /**
     * 普通列表查询接口，不分页
     *
     * @param query: 条件参数
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryList(Map<String, Object> query);
}
