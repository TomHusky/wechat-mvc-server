package com.tomhusky.wechatmvc.server.controller;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.service.base.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

}
