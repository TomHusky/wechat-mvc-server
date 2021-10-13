package com.tomhusky.wechatmvc.server.controller;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.service.AccountService;
import com.tomhusky.wechatmvc.server.service.EmailService;
import com.tomhusky.wechatmvc.server.service.UserService;
import com.tomhusky.wechatmvc.server.vo.add.AddAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/registerAccount")
    public JsonResult<Integer> registerAccount(@RequestBody @Validated AddAccountVo accountVo) {
        boolean validCode = emailService.validCode(accountVo.getEmail(), accountVo.getCode());
        if (!validCode) {
            return JsonResult.fail("验证码不正确");
        }
        return JsonResult.success(accountService.addAccount(accountVo));
    }

    /**
     * 判断邮箱是否存在
     *
     * @param email 邮箱地址
     */
    @GetMapping("/emailExit")
    public JsonResult<Boolean> emailExit(@RequestParam String email) {
        return JsonResult.success(userService.emailExit(email));
    }

    /**
     * 判断账号是否存在
     *
     * @param username 账号
     */
    @GetMapping("/usernameExit")
    public JsonResult<Boolean> usernameExit(@RequestParam String username) {
        return JsonResult.success(userService.getUserByName(username) != null);
    }

}
