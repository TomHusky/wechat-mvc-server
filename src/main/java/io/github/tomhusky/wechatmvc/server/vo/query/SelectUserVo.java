package io.github.tomhusky.wechatmvc.server.vo.query;

import lombok.Data;

/**
 * <p>
 * 查找用户信息返回视图
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/23 17:31
 */
@Data
public class SelectUserVo {
    /**
     * 用户名
     */
    private String username;

    /**
     * wxid
     */
    private String wxid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 地址
     */
    private String area;
}
