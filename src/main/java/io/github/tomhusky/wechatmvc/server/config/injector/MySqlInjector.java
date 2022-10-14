package io.github.tomhusky.wechatmvc.server.config.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;


/**
 * @author caolei
 * @version 1.0
 * @since 2018/11/27
 * 自定义mybatis-plus插件
 */
public class MySqlInjector extends DefaultSqlInjector {


    /**
     * <p>
     * 获取 注入的方法
     * </p>
     *
     * @param mapperClass 当前mapper
     * @return 注入的方法集合
     * @since 3.1.2 add  mapperClass
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        //保留原有的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new MyInsertList());
        return methodList;
    }
}