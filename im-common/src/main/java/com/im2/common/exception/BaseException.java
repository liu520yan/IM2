package com.im2.common.exception;

import com.im2.common.error.ResponseType;

/**
 * Created by liuyan on 2018/9/14.
 */
public class BaseException extends RuntimeException {

    private String message;
    private ResponseType errerType;

    public BaseException(String message, ResponseType type) {
        this.message = message;
        this.errerType = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseType getErrerType() {
        return errerType;
    }

    public void setErrerType(ResponseType errerType) {
        this.errerType = errerType;
    }
}
