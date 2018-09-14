package com.im2.common.baseBean;

import com.im2.common.error.BaseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by liuyan on 2018/9/14.
 */
public class BaseController {
    protected static final ResponseEntity SUCCESS = new ResponseEntity<>(new BaseMessage("成功","请求成功","0000"), HttpStatus.OK);
}
