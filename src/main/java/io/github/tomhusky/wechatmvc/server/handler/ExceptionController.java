package io.github.tomhusky.wechatmvc.server.handler;

import io.github.tomhusky.wechatmvc.server.common.exception.BaseException;
import io.github.tomhusky.wechatmvc.server.common.exception.LockException;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.common.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author luowj
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {


    /**
     * 参数校验错误
     */
    @ExceptionHandler(BindException.class)
    public JsonResult validExceptionHandler(BindException e) {
        log.error("参数类型错误：", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errMsg;
        if (null != fieldError) {
            errMsg = fieldError.getDefaultMessage();
        } else {
            errMsg = "请求参数有误";
        }
        return JsonResult.fail(errMsg);
    }


    /**
     * 数据操作处理异常
     */
    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public JsonResult handleDataAccessException(DataAccessException e) {
        log.error("数据库操作错误：", e);
        return JsonResult.fail("操作异常");
    }


    /**
     * 参数校验错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String errMsg;
        if (result.hasErrors() && null != result.getFieldError()) {
            errMsg = result.getFieldError().getDefaultMessage();
        } else {
            errMsg = "请求参数有误";
        }
        log.error("异常：{}", errMsg);
        return JsonResult.fail(errMsg);
    }

    /**
     * 捕捉token异常
     */
    @ExceptionHandler(JwtException.class)
    public JsonResult tokenException(JwtException ex) {
        log.error("【token异常】,{}", ex.getMessage());
        return JsonResult.failByMsg(ex.getCode(), ex.getMessage());
    }

    /**
     * redisson 锁异常
     */
    @ExceptionHandler(LockException.class)
    public JsonResult lockException(LockException ex) {
        log.error("【redisson 锁异常】,{}", ex.getMessage());
        return JsonResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 捕捉baseException异常
     */
    @ExceptionHandler(BaseException.class)
    public JsonResult baseException(BaseException ex) {
        log.error("异常：", ex);
        return JsonResult.fail( ex.getMessage());
    }

    /**
     * 捕捉其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public JsonResult globalException(HttpServletRequest request, Exception ex) {
        log.error("异常：", ex);
        return JsonResult.failByMsg(getStatus(request).value(), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}

