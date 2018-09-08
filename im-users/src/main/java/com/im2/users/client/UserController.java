package com.im2.users.client;

import com.im2.common.entity.UserEntity;
import com.im2.users.dao.UserDao;
import com.im2.users.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by liuyan on 2018/8/23.
 */
@Api(value = "用户", description = "用户接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserDao userDao;
    @Value("${mob.service.appkey}")
    private String appkey;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "注册")
    @RequestMapping(method = RequestMethod.POST)
    void register(@RequestBody UserVo user) throws Exception {
        //todo 验证码验证
        if (!verifyCodeCheck(user.getPhone(), user.getVerifyCode())) {
            throw new Exception("验证码错误");
        }
        userDao.insertByUser(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    UserEntity findUser(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        return userDao.findUserByNameAndPsd(username, password);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/username")
    UserEntity findUserByName(@RequestParam("username") String username) {
        return userDao.findUserByName(username);
    }

    public Boolean verifyCodeCheck(String phone, String verifyCode) {
        String url = "https://webapi.sms.mob.com/sms/verify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("appkey", appkey);
        params.add("phone", phone);
        params.add("zone", "86"); //目前只支持86
        params.add("code", verifyCode);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return HttpStatus.OK.equals(response.getStatusCode());
    }

}
