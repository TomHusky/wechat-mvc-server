package com.tomhusky.wechatmvc.server.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lwj
 * @version 1.0
 * @since 2018/1/5
 */
@Getter
@Setter
public class PageVo<T> implements Serializable {

    /**
     * 数量
     */
    private List<T> records;
    /**
     * sql 条件
     */
    private Map<String, Object> condition = new HashMap<>();
    /**
     * 开始页数
     */
    private int current = 0;
    /**
     * 每页的数据量
     */
    private int size = 10;

    /**
     * 用来计算起始的pageNum
     */
    private Integer startLimit;
    /**
     * 总数
     */
    private long total = 0;


    /**
     * pageVo转换成mybatis-plus的分页参数
     *
     * @param pageVo
     * @return
     */
    public static Page coverPage(PageVo pageVo) {
        if (pageVo != null) {
            return new Page(pageVo.getCurrent(), pageVo.getSize());
        }
        return null;
    }


    /**
     * 初始化分页
     *
     * @param pageVo
     * @return
     */
    public static PageVo initPage(PageVo pageVo) {
        int maxSize = 40;
        if (pageVo.getCurrent() < 0) {
            pageVo.setCurrent(0);
        } else if (pageVo.getCurrent() > 0) {
            pageVo.setCurrent(pageVo.getCurrent() - 1);
        }
        if (pageVo.getSize() < 0) {
            pageVo.setSize(10);
        } else if (pageVo.getSize() > maxSize) {
            pageVo.setSize(maxSize);
        }
        return pageVo;
    }


}
