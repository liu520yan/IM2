package com.im2.common.error;

import com.im2.common.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by liuyan on 2018/9/14.
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseMessage implements Serializable {
    private String message;
    private String describe;
    private String code;

    public BaseMessage(BaseException e) {
        this.describe = e.getMessage();
        this.message = e.getErrerType().getValue();
        this.code = e.getErrerType().getKey();
    }
}
