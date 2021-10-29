package io.github.tomhusky.wechatmvc.server.common.enums;

/**
 * 群成员 身份类型
 *
 * @author luowj
 * @since 2021/9/13 13:37
 */
public enum GroupUserType {
    /*
     * 普通成员
     */
    NORMAL(1),
    /*
     * 群主
     */
    OWNER(0),
    /*
     * 管理员
     */
    ADMIN(2);

    private final int value;

    GroupUserType(int value) {
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
        for (GroupUserType e : GroupUserType.values()) {
            if (e.getValue() == value) {
                include = true;
                break;
            }
        }
        return include;
    }
}
