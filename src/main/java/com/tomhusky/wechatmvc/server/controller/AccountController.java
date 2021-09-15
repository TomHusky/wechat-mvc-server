package com.tomhusky.wechatmvc.server.controller;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.service.AccountService;
import com.tomhusky.wechatmvc.server.vo.add.AddAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luowj
 * @className: UserController
 * @date 2021/9/15 11:31
 * @version：1.0
 * @description: 用户接口
 */
@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/registerAccount")
    public JsonResult<Boolean> registerAccount(@RequestBody @Validated AddAccountVo accountVo) {
        return JsonResult.success(accountService.addAccount(accountVo));
    }
}
