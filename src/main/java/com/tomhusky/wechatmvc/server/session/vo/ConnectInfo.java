package com.tomhusky.wechatmvc.server.session.vo;

import lombok.Data;

/**
 * <p>
 * 连接信息
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/24 11:50
 */
@Data
public class ConnectInfo {

    /**
     * 登录token
     */
    private String token;

    /**
     * 用户名
     */
    private String username;

}
