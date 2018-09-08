package com.im2.common.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by liuyan on 2018/9/6.
 */
@FeignClient("oauth2")
public interface Oauth2Feign {
//    @Headers({"Authorization: {base64}"})
//    @RequestMapping(method = RequestMethod.GET, value = "/oauth/check_token")
//    Object checkToken(@RequestParam(name = "token") String token, @Param("base64") String base64);
//
//    @Headers({"Authorization: Basic YWNtZTphY21lc2VjcmV0"})
//    @RequestMapping(method = RequestMethod.GET, value = "/oauth/check_token")
//    Object checkToken(@RequestParam(name = "token") String token);
//
////    @Headers({"Authorization: Basic YWNtZTphY21lc2VjcmV0"})
//    @RequestMapping(method = RequestMethod.GET, value = "/oauth/check_token")
//    Object checkToken(@RequestParam(name = "token") String token, @HeaderMap Map<String, String> headerMap);
//
//    //    @Headers({": Basic YWNtZTphY21lc2VjcmV0"})
    @RequestMapping(method = RequestMethod.GET, value = "/oauth/check_token")
    Map<String, Object> checkToken(@RequestParam(name = "token") String token, @RequestHeader(name = "Authorization", required = false) String header);
}
