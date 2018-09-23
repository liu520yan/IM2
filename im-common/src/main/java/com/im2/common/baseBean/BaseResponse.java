package com.im2.common.baseBean;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyan on 2018/9/19.
 */
@Getter
@Setter
public class BaseResponse extends ResponseEntity {
    private String code;
    private Object data;

    public BaseResponse(HttpStatus status, ResponseEntity entity, String code) {
        super(status);
        this.data = entity.getBody();
        this.code = code;
    }

    public BaseResponse(HttpStatus status) {
        super(status);
    }

    public BaseResponse(Object body, HttpStatus status) {
        super(body, status);
    }

    public BaseResponse(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public BaseResponse(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        ResponseEntity entity = new ResponseEntity<>(map, HttpStatus.OK);

        BaseResponse baseResponse = new BaseResponse(HttpStatus.OK, entity, "200");
        System.out.println(JSON.toJSON(baseResponse));
    }
}
