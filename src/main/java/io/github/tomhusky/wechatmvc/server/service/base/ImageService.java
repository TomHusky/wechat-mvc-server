package io.github.tomhusky.wechatmvc.server.service.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 图片处理接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 9:33
 */
public interface ImageService {

    /**
     * 保存图片
     *
     * @param fileName 文件名称
     * @param file     文件
     * @return java.lang.String
     */
    String saveImg(String fileName, File file);

    /**
     * 保存图片
     *
     * @param fileName 文件名称
     * @param img      图片
     * @return java.lang.String
     */
    String saveImg(String fileName, BufferedImage img);


    /**
     * 生成群聊图片
     *
     * @param images  图片列表
     * @param groupNo 群聊编号
     * @return java.lang.String
     */
    String getGroupAvatar(List<String> images, String groupNo);

}
