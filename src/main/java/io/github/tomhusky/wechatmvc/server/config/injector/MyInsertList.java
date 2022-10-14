package io.github.tomhusky.wechatmvc.server.config.injector;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * @author caolei
 * 自定义批量插入接口
 */

public class MyInsertList extends AbstractMethod {

    /**
     * 批量insert表数据,注意，当前没有处理大批量分段处理，请调用方法前，自行分段
     * 理论上，长度由于表字段限制，mysql当前执行sql长度限制由my.cnf配置，超过会出现异常
     *
     * @param mapperClass
     * @param modelClass
     * @param table
     * @return
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {

        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder placeholderBuilder = new StringBuilder();
        //添加插入值数据
        StringBuilder subSqlBuild = new StringBuilder();
        StringBuilder valuesBuild = new StringBuilder();
        MySqlMethod mySqlMethod = MySqlMethod.INSERT_LIST;

        fieldBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        placeholderBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        String keyProperty = null;
        String keyColumn = null;
        KeyGenerator keyGenerator = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotEmpty(table.getKeyProperty())) {
            keyGenerator = new NoKeyGenerator();
            if (table.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = table.getKeyProperty();
                keyColumn = table.getKeyColumn();
            } else {
                if (null != table.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(mySqlMethod.getMethod(), table, builderAssistant);
                    keyProperty = table.getKeyProperty();
                    keyColumn = table.getKeyColumn();
                    fieldBuilder.append(table.getKeyColumn()).append(",");
                    placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
                    valuesBuild.append("#{item.").append(table.getKeyProperty()).append("},");
                } else {
                    /* 用户输入自定义ID */
                    fieldBuilder.append(table.getKeyColumn()).append(",");
                    // 正常自定义主键策略
                    placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
                    valuesBuild.append("#{item.").append(table.getKeyProperty()).append("},");
                }
            }
        }
        List<TableFieldInfo> fieldList = table.getFieldList();
        for (TableFieldInfo fieldInfo : fieldList) {
            fieldBuilder.append(fieldInfo.getColumn()).append(",");
            placeholderBuilder.append("#{").append(fieldInfo.getEl()).append("},");
            valuesBuild.append("#{item.").append(fieldInfo.getEl()).append("},");
        }
        subSqlBuild.append("\n<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">");
        subSqlBuild.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n").append(valuesBuild).append("\n</trim>");
        subSqlBuild.append("\n</foreach>");

        fieldBuilder.append("\n</trim>");
        placeholderBuilder.append("\n</trim>");
        String sql = String.format(mySqlMethod.getSql(), table.getTableName(), fieldBuilder, subSqlBuild);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addInsertMappedStatement(mapperClass, modelClass, MySqlMethod.INSERT_LIST.getMethod(), sqlSource, keyGenerator, keyProperty, keyColumn);
    }
}
