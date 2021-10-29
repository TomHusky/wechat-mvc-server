package io.github.tomhusky.wechatmvc.server.common.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import sun.awt.shell.ShellFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>
 * 系统图标帮助类
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/26 14:33
 */
@Slf4j
public class SystemIconUtil {

    private SystemIconUtil() {

    }

    /**
     * 根据拓展名获取文件图标
     *
     * @param extension 拓展名
     * @return java.awt.image.BufferedImage
     */
    public static BufferedImage getImageByFileType(String extension) {
        File file = null;
        extension = extension.toLowerCase();
        try {
            file = File.createTempFile("icon", extension);
            return toBufferedImage(toImage(toIcon(file)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            FileUtil.del(file);
        }
        return null;
    }

    private static Icon toIcon(File file) throws FileNotFoundException {
        ShellFolder shellFolder = ShellFolder.getShellFolder(file);
        return new ImageIcon(shellFolder.getIcon(true));
    }

    private static Image toImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }

    private static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage(), e);
        }
        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    // This method returns a buffered image with the contents of an image
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image
                    .getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
            log.error(e.getMessage(), e);
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image
                    .getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
}
