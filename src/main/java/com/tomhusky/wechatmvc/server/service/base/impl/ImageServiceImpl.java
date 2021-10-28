package com.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.io.FileUtil;
import com.tomhusky.wechatmvc.server.common.util.ImageUtils;
import com.tomhusky.wechatmvc.server.service.base.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 图片处理接口实现类
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 9:34
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${file.rootPath}")
    private String rootPath;

    @Value("${file.baseUrl}")
    private String baseUrl;

    @Override
    public String saveImg(String fileName, File file) {
        File saveFile = new File(rootPath + "img\\" + fileName);
        if (!saveFile.exists()) {
            FileUtil.touch(saveFile);
        }
        FileUtil.copy(file, saveFile, true);
        return baseUrl + "img/" + fileName;
    }

    @Override
    public String saveImg(String fileName, BufferedImage img) {
        ImageUtils.generateWaterFile(img, rootPath + "\\img\\" + fileName);
        return baseUrl + "img/" + fileName;
    }

    @Override
    public String getGroupAvatar(List<String> images, String groupNo) {
        String fileUrl = baseUrl;
        if (images.size() <= 4) {
            fileUrl = fileUrl + composeFour(images, groupNo);
        } else if (images.size() <= 6) {
            fileUrl = fileUrl + composeSix(images, groupNo);
        } else {
            fileUrl = fileUrl + composeNine(images, groupNo);
        }
        return fileUrl;
    }

    private String composeFour(List<String> images, String groupNo) {
        String filePath = rootPath + groupNo + ".jpg";
        String fileName = groupNo + ".jpg";
        try {
            List<ImageUtils.PressImgVO> pressImgVOList = new ArrayList<>();
            int x;
            int y;
            int line = 32;
            for (int i = 0; i < images.size(); i++) {
                String url = images.get(i);
                BufferedImage image = ImageIO.read(new URL(url));
                ImageUtils.PressImgVO pressImgVO = new ImageUtils.PressImgVO();
                pressImgVO.setBufferedImage(image);
                pressImgVO.setHeight(line);
                pressImgVO.setWidth(line);
                if (i < 2) {
                    y = 0;
                    x = (i) * line;
                } else {
                    y = line;
                    x = (i - 2) * line;
                }
                pressImgVO.setX(x);
                pressImgVO.setY(y);
                pressImgVOList.add(pressImgVO);
            }
            BufferedImage bufferedImage = ImageUtils.pressImage(pressImgVOList, 64, 64, 1);
            ImageUtils.generateWaterFile(bufferedImage, filePath);
            return fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private String composeSix(List<String> images, String groupNo) {
        String filePath = rootPath + groupNo + ".jpg";
        String fileName = groupNo + ".jpg";
        try {
            List<ImageUtils.PressImgVO> pressImgVOList = new ArrayList<>();
            int x;
            int y;
            int line = 20;
            for (int i = 0; i < images.size(); i++) {
                String url = images.get(i);
                BufferedImage image = ImageIO.read(new URL(url));
                ImageUtils.PressImgVO pressImgVO = new ImageUtils.PressImgVO();
                pressImgVO.setBufferedImage(image);
                pressImgVO.setHeight(line);
                pressImgVO.setWidth(line);
                if (i < 3) {
                    y = 10;
                    x = (i) * line;
                } else {
                    y = line + 10 + 2;
                    x = (i - 3) * line;
                }
                if (x != 0) {
                    x = x + 2;
                }
                pressImgVO.setX(x);
                pressImgVO.setY(y);
                pressImgVOList.add(pressImgVO);
            }
            BufferedImage bufferedImage = ImageUtils.pressImage(pressImgVOList, 64, 64, 1);
            ImageUtils.generateWaterFile(bufferedImage, filePath);
            return fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private String composeNine(List<String> images, String groupNo) {
        String filePath = rootPath + groupNo + ".jpg";
        String fileName = groupNo + ".jpg";
        try {
            List<ImageUtils.PressImgVO> pressImgVOList = new ArrayList<>();
            int x;
            int y;
            int line = 16;
            for (int i = 0; i < images.size(); i++) {
                String url = images.get(i);
                BufferedImage image = ImageIO.read(new URL(url));
                ImageUtils.PressImgVO pressImgVO = new ImageUtils.PressImgVO();
                pressImgVO.setBufferedImage(image);
                pressImgVO.setHeight(line);
                pressImgVO.setWidth(line);
                if (i < 3) {
                    y = 0;
                    x = (i) * line;
                } else if (i < 6) {
                    y = line;
                    x = (i - 3) * line;
                } else {
                    y = line * (i - 1);
                    x = (i - 3) * line;
                }
                if (x != 0) {
                    x = x + 2;
                }
                if (y != 0) {
                    y = y + 2;
                }
                pressImgVO.setX(x);
                pressImgVO.setY(y);
                pressImgVOList.add(pressImgVO);
            }
            BufferedImage bufferedImage = ImageUtils.pressImage(pressImgVOList, 64, 64, 1);
            ImageUtils.generateWaterFile(bufferedImage, filePath);
            return fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
