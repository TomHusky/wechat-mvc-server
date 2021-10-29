package io.github.tomhusky.wechatmvc.server.common.enums;

/**
 * <p>
 * 申请状态
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 11:34
 */
public enum ApplyStatus {
    APPLYING(0, "申请中"),
    Added(1, "已添加"),
    refuse(2, "拒绝");
    private final Integer value;

    private final String info;

    ApplyStatus(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }
}
