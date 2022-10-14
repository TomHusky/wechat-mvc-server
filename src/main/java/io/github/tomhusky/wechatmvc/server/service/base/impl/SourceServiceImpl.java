package io.github.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.biejieshi.compress.PngCompressUtils;
import io.github.tomhusky.wechatmvc.server.common.util.ImageUtils;
import io.github.tomhusky.wechatmvc.server.service.base.SourceService;
import io.github.tomhusky.wechatmvc.server.common.exception.OperateException;
import io.github.tomhusky.wechatmvc.server.common.util.SystemIconUtil;
import io.github.tomhusky.wechatmvc.server.service.base.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    @Value("${file.rootPath}")
    private String rootPath;

    @Value("${file.baseUrl}")
    private String baseUrl;

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
            if (".gif".equalsIgnoreCase(fileType)) {
                reqFile = File.createTempFile(name, fileType);
                out = new FileOutputStream(reqFile);
                IoUtil.copy(file.getInputStream(), out);
                resultMap.put("url", imageService.saveImg(reqFile.getName(), reqFile));
            } else if (".png".equalsIgnoreCase(fileType)) {
                BufferedImage bufferedImage = PngCompressUtils.compressPng(sourceImg);
                resultMap.put("url", imageService.saveImg(name + fileType, bufferedImage));
            } else {
                reqFile = File.createTempFile(name, fileType);
                out = new FileOutputStream(reqFile);
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

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = IdUtil.simpleUUID();
        fileName = fileName + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'));
        try {
            File saveFile = new File(rootPath + "\\file", fileName);
            if (!saveFile.exists()) {
                FileUtil.touch(saveFile);
            }
            FileUtil.writeFromStream(file.getInputStream(), saveFile, true);
            return baseUrl + "file/" + fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Map<String, String> listSystemIconBase64() {
        String[] extensions = {".txt", ".html", ".pdf", ".xlsx", ".docx", ".pptx", ".exe", ".java", ".zip", ".rar", ".json"};
        Map<String, String> iconList = new HashMap<>();
        for (String extension : extensions) {
            BufferedImage imageByFileType = SystemIconUtil.getImageByFileType(extension);

            ImageUtils.generateWaterFile(imageByFileType, "D:\\tool\\system-icon\\" + extension.substring(1) + extension);
            if (imageByFileType != null) {
                iconList.put(extension, "data:image/png;base64," + ImageUtils.base64(imageByFileType, "png"));
            }
        }
        return iconList;
    }
}
