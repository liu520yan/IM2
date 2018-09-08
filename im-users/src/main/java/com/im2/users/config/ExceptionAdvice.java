package com.im2.users.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 接口异常处理
 *
 * @author 郑喜荣
 * @since 2016/11/3
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleValException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
