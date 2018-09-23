package com.im2.oauth2.response;

import com.im2.common.baseBean.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * Created by liuyan on 2018/9/19.
 */
@Slf4j
//@Aspect
//@Component
public class ResponseAdapter {

    public static final String EDP="execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint..*(..))";

    /**
     * 切入点：cn.ictgu.controller 下所有 @GetMapping 方法
     */
    @Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint..*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @After(value=EDP)
    public BaseResponse doAfter(JoinPoint jp) {
        log.info("-----------------修改tokeEndPoint返回类型-------------------");
        ResponseEntity<OAuth2AccessToken> target = (ResponseEntity<OAuth2AccessToken>) jp.getTarget();
        return new BaseResponse(HttpStatus.OK, target, "200");
    }
}
