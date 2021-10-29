package io.github.tomhusky.wechatmvc.server.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {

    private Long total;
    private int pageSize;
    private int pageNum;
    private List<T> list;

    /**
     * mybatisPlus自带分页插件参数构造器
     */
    public PageList(IPage<T> iPage) {
        this.total = iPage.getTotal();
        this.pageSize = (int) iPage.getSize();
        this.pageNum = (int) iPage.getCurrent();
        this.list = iPage.getRecords();
    }
}