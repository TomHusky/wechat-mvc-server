package com.tomhusky.wechatmvc.server.service.base;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 资源服务接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/21 13:37
 */
public interface SourceService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return java.util.Map<java.lang.String, java.lang.Object> 文件地址和大小
     */
    Map<String, Object> uploadImg(MultipartFile file);

    /**
     * 上传文件
     *
     * @param file 文件地址
     * @return java.lang.String
     */
    String uploadFile(MultipartFile file);

    /**
     * 获取系统图标base64
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    Map<String, String> listSystemIconBase64();
}