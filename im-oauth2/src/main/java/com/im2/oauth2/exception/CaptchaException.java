package com.im2.oauth2.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by liuyan on 2017/12/7.
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public CaptchaException(String msg) {
        super(msg, null);
    }
}
