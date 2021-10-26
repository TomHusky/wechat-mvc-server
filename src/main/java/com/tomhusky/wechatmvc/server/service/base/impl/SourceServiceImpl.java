package com.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.biejieshi.compress.PngCompressUtils;
import com.tomhusky.wechatmvc.server.common.exception.OperateException;
import com.tomhusky.wechatmvc.server.service.base.ImageService;
import com.tomhusky.wechatmvc.server.service.base.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 资源服务接口实现类
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/21 13:38
 */
@Slf4j
@Service
public class SourceServiceImpl implements SourceService {

    @Autowired
    private ImageService imageService;

    @Override
    public Map<String, Object> uploadImg(@RequestParam MultipartFile file) {
        File reqFile = null;
        FileOutputStream out = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String name = IdUtil.simpleUUID();
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'));
            BufferedImage sourceImg = ImageIO.read(file.getInputStream());
            if (".png".equals(fileType)) {
                BufferedImage bufferedImage = PngCompressUtils.compressPng(sourceImg);
                resultMap.put("url", imageService.saveImg(name + fileType, bufferedImage));
            } else {
                reqFile = File.createTempFile(name, fileType);
                out = new FileOutputStream(reqFile);
//                IoUtil.copy(file.getInputStream(), out);
                // 图片压缩
                Img.from(sourceImg).setQuality(0.5).write(out);
                resultMap.put("url", imageService.saveImg(reqFile.getName(), reqFile));
            }
            resultMap.put("width", sourceImg.getWidth());
            resultMap.put("height", sourceImg.getHeight());
            return resultMap;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new OperateException("上传失败");
        } finally {
            IoUtil.close(out);
            FileUtil.del(reqFile);
        }
    }
}
