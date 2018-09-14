package com.im2.users.client;

import com.im2.common.error.BaseMessage;
import com.im2.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by liuyan on 2018/9/14.
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResponseEntity<BaseMessage> handleMethodArgumentTypeMismatchException(BaseException e) {
        return new ResponseEntity<>(new BaseMessage(e), HttpStatus.BAD_REQUEST);
    }
}
