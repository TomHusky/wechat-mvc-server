package io.github.tomhusky.wechatmvc.server.controller;

import io.github.tomhusky.wechatmvc.server.service.base.SourceService;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 资源相关接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/21 12:21
 */
@Slf4j
@RequestMapping("source")
@RestController
public class SourceController {

    @Autowired
    private SourceService sourceService;

    @PostMapping(value = "uploadImg")
    public JsonResult<Map<String, Object>> uploadImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> uploadImg = sourceService.uploadImg(file);
        if (uploadImg == null) {
            return JsonResult.fail("发送失败");
        }
        return JsonResult.success(uploadImg);
    }

    @PostMapping(value = "uploadFile")
    public JsonResult<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String uploadFile = sourceService.uploadFile(file);
        if (uploadFile == null) {
            return JsonResult.fail("发送失败");
        }
        return JsonResult.success(uploadFile);
    }

    @GetMapping(value = "listSystemIcon")
    public JsonResult<Map<String, String>> listSystemIcon() {
        Map<String, String> iconMap = sourceService.listSystemIconBase64();
        if (iconMap == null) {
            return JsonResult.fail("获取失败");
        }
        return JsonResult.success(iconMap);
    }


}
