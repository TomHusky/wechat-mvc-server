package io.github.tomhusky.wechatmvc.server.common.base;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.tomhusky.wechatmvc.server.common.PageList;
import io.github.tomhusky.wechatmvc.server.common.PageVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author: lwj
 * @Package: com.meishang.wxcp.common.base.impl
 * @ClassName: BaseServiceImpl
 * @CreateDate: 2021/7/16 15:23
 * @Description: 基础服务增强实现类
 */
public class BaseServiceImpl<M extends CommonMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Autowired
    protected M mapper;

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        int size = 0;
        List<T> allList = new ArrayList<>(entityList);
        if (entityList.size() > DEFAULT_BATCH_SIZE) {
            List<List<T>> splitList = CollectionUtil.split(allList, DEFAULT_BATCH_SIZE);
            for (List<T> list : splitList) {
                size += mapper.insertBatchSomeColumn(list);
            }
        } else {
            size = mapper.insertBatchSomeColumn(entityList);
        }
        return size == entityList.size();
    }

    @Override
    public PageList<T> queryPage(PageVo pageVO) {
        IPage<T> tiPage = mapper.queryPage(PageVo.coverPage(pageVO), PageVo.coverPageParams(pageVO));
        return new PageList<>(tiPage);
    }

    @Override
    public PageList<?> queryPageVo(PageVo pageVO) {
        IPage<?> iPage = mapper.queryPageVo(PageVo.coverPage(pageVO), PageVo.coverPageParams(pageVO));
        return new PageList<>(iPage);
    }

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> query) {
        return mapper.queryList(query);
    }
}
