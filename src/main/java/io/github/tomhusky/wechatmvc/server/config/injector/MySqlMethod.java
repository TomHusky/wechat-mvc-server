package io.github.tomhusky.wechatmvc.server.config.injector;

/**
 * @author caolei
 * @version 1.0
 * @since 2018/11/27
 */
public enum MySqlMethod {
    /**
     * 插入
     */
    INSERT_LIST("insertList", "批量插入数据，非一条一条插入", "<script>INSERT INTO %s %s VALUES %s</script>");

    private final String method;
    private final String desc;
    private final String sql;

    MySqlMethod(final String method, final String desc, final String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
