package io.github.tomhusky.wechatmvc.server.common.enums;

/**
 * 消息类型
 *
 * @author luowj
 * @since 2021/9/13 13:37
 */
public enum MsgType {
    /*
     * 好友
     */
    FRIEND(1),
    /*
     * 群
     */
    GROUP(2),
    /*
     * 公众号
     */
    PUBLIC(3);

    private final int value;

    MsgType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * 判断数值是否属于枚举类的值
     *
     * @param value 值
     * @return 是否是枚举中的值
     */
    public static boolean isInclude(int value) {
        boolean include = false;
        for (MsgType e : MsgType.values()) {
            if (e.getValue() == value) {
                include = true;
                break;
            }
        }
        return include;
    }
}
