package io.github.tomhusky.wechatmvc.server.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author: lwj
 * @Package: com.meishang.wxcp.common.base
 * @interfaceName: BaseDao
 * @CreateDate: 2021/7/16 15:04
 * @Description: 加强mapper
 */
public interface CommonMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入（mysql）
     *
     * @param entityList 实体类对象集合
     * @return 受影响的行数
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);

    /**
     * 分页查询
     *
     * @param page:  分页参数
     * @param query: 条件参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     */
    IPage<T> queryPage(Page<?> page, @Param("query") Map<String, Object> query);

    /**
     * 分页查询,返回指定VO
     *
     * @param page:  分页参数
     * @param query: 条件参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     */
    IPage<?> queryPageVo(Page<?> page, @Param("query") Map<String, Object> query);

    /**
     * 普通列表查询接口，不分页
     *
     * @param query: 条件参数
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryList(@Param("query") Map<String, Object> query);

}
