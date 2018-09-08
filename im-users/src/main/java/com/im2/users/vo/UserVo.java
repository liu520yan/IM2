package com.im2.users.vo;


import com.im2.common.entity.UserEntity;

/**
 * Created by liuyan on 2018/9/7.
 */
public class UserVo extends UserEntity {
    private String verifyCode;//验证码

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
