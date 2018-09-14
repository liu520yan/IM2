package com.im2.common.error;

/**
 * Created by liuyan on 2018/9/14.
 */
public enum ResponseType {
    BIZE_RRER("0001", "业务异常"),  //业务异常
    SERVIC_EERRER("9999", "服务器异常"),  //服务器异常
    PARAM_ERRER("0002", "参数异常"),  //参数异常
    SUCCESS("0004", "成功") //成功
    ;

    private final String key;
    private final String value;


    ResponseType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ResponseType getEnumByKey(String key) {
        if (null == key) {
            return null;
        }
        for (ResponseType temp : ResponseType.values()) {
            if (temp.getKey().equals(key)) {
                return temp;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
