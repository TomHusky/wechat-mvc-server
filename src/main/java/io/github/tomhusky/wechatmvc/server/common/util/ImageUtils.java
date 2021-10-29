package io.github.tomhusky.wechatmvc.server.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 图片工具类
 *
 * @author luowj
 * @date 2021/8/27 15:50
 */
@Slf4j
public class ImageUtils {

    public static String base64(File file) {
        byte[] bytes = FileUtil.readBytes(file);
        return Base64.encode(bytes);
    }

    public static String base64(BufferedImage image, String type) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();) {
            ImageIO.write(image, type, stream);
            return Base64.encode(stream.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 构造图片 生成水印并返回java.awt.image.BufferedImage
     *
     * @param srcImage 源文件(图片)
     * @param pressImg 水印文件(图片)
     * @param x        距离右下角的X偏移量
     * @param y        距离右下角的Y偏移量
     * @param alpha    透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage pressImage(File srcImage, File pressImg, int x, int y, float alpha) throws IOException {
        // 获取底图
        BufferedImage buffImg = ImageIO.read(srcImage);
        // 获取层图
        BufferedImage waterImg = ImageIO.read(pressImg);
        return pressImage(buffImg, waterImg, x, y, alpha);
    }

    /**
     * 构造图片 生成水印并返回java.awt.image.BufferedImage
     *
     * @param srcImage 源文件(图片) 底图
     * @param pressImg 水印文件(图片) 顶层图
     * @param x        距离右下角的X偏移量
     * @param y        距离右下角的Y偏移量
     * @param alpha    透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     */
    public static BufferedImage pressImage(BufferedImage srcImage, BufferedImage pressImg, int x, int y, float alpha) {
        // 获取底层图的宽度
        int imgWidth = pressImg.getWidth();
        // 获取底层图的高度
        int imgHeight = pressImg.getHeight();
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = img.createGraphics();
        // 获取层图的宽度
        int waterImgWidth = pressImg.getWidth();
        // 获取层图的高度
        int waterImgHeight = pressImg.getHeight();
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制底图
        g2d.drawImage(srcImage, 0, 0, imgWidth, imgHeight, null);
        // 绘制水印图
        g2d.drawImage(pressImg, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return img;
    }


    /**
     * 构造图片 生成水印并返回java.awt.image.BufferedImage
     *
     * @param pressImgList 水印文件(图片) 列表
     * @param alpha        透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     */
    public static BufferedImage pressImage(List<PressImgVO> pressImgList, int width, int height, float alpha) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(new Color(221, 222, 224));
        //白色填充整个屏幕
        g2d.fillRect(0, 0, width, height);
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        for (PressImgVO pressImg : pressImgList) {
            // 绘制水印图
            g2d.drawImage(pressImg.bufferedImage, pressImg.x, pressImg.y, pressImg.width, pressImg.height, null);
        }
        g2d.dispose();// 释放图形上下文使用的系统资源
        return img;
    }

    /**
     * 指定长和宽对图片进行缩放
     *
     * @param srcImage 源文件(图片)
     * @param width    长
     * @param height   宽
     * @throws IOException
     */
    public static BufferedImage scaled(File srcImage, int width, int height) throws IOException {
        BufferedImage img = ImageIO.read(srcImage);
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(scaled, 0, 0, null);
        graphics.dispose();
        return image;
    }

    /**
     * 指定长和宽对图片进行缩放
     *
     * @param srcImage 源文件(图片)
     * @param width    长
     * @param height   宽
     * @throws IOException
     */
    public static void scaled(File srcImage, File outFile, int width, int height) throws IOException {
        BufferedImage scaled = scaled(srcImage, width, height);
        generateWaterFile(scaled, outFile.getPath());
    }

    /**
     * 构造图片 生成水印并返回java.awt.image.BufferedImage
     *
     * @param srcImage 源文件(图片)
     * @param outFile  输出文件(图片)
     * @param pressImg 水印文件(图片)
     * @param x        距离右下角的X偏移量
     * @param y        距离右下角的Y偏移量
     * @param alpha    透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     */
    public static void pressImage(File srcImage, File outFile, File pressImg, int x, int y, float alpha) throws IOException {
        BufferedImage buffImg = pressImage(srcImage, pressImg, x, y, alpha);
        // 输出到文件
        generateWaterFile(buffImg, outFile.getPath());
    }

    public static void pressImage(BufferedImage srcImage, File outFile, File pressImg, int x, int y, float alpha) throws IOException {
        BufferedImage buffImg = pressImage(srcImage, ImageIO.read(pressImg), x, y, alpha);
        // 输出到文件
        generateWaterFile(buffImg, outFile.getPath());
    }

    /**
     * 构造图片 生成水印并返回java.awt.image.BufferedImage
     *
     * @param srcImage 源文件(图片)
     * @param outFile  输出文件(图片)
     * @param pressImg 水印文件(图片)
     * @param x        距离右下角的X偏移量
     * @param y        距离右下角的Y偏移量
     * @param alpha    透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     */
    public static void pressImage(BufferedImage srcImage, File outFile, BufferedImage pressImg, int x, int y, float alpha) {
        BufferedImage buffImg = pressImage(srcImage, pressImg, x, y, alpha);
        // 输出到文件
        generateWaterFile(buffImg, outFile.getPath());
    }

    public static void pressImage(File srcImage, File outFile, BufferedImage pressImg, int x, int y, float alpha) throws IOException {
        BufferedImage buffImg = pressImage(ImageIO.read(srcImage), pressImg, x, y, alpha);
        // 输出到文件
        generateWaterFile(buffImg, outFile.getPath());
    }

    /**
     * 输出水印图片
     *
     * @param buffImg  图像加水印之后的BufferedImage对象
     * @param savePath 图像加水印之后的保存路径
     */
    public static void generateWaterFile(BufferedImage buffImg, String savePath) {
        File file = new File(savePath);
        if (!file.exists()) {
            FileUtil.touch(file);
        }
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            ImageIO.write(buffImg, savePath.substring(temp), file);
        } catch (IOException e1) {
            log.error(e1.getMessage(), e1);
        }
    }

    public static class PressImgVO {

        private BufferedImage bufferedImage;

        private int x;

        private int y;

        private int width;

        private int height;

        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        public void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
