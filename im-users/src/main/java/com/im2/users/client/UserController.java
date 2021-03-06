package com.im2.users.client;

import com.im2.common.baseBean.BaseController;
import com.im2.common.entity.UserEntity;
import com.im2.common.error.ResponseType;
import com.im2.common.exception.BaseException;
import com.im2.common.time.Timer;
import com.im2.common.util.AccountValidatorUtil;
import com.im2.users.dao.UserDao;
import com.im2.users.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyan on 2018/8/23.
 */
@Slf4j
@Api(value = "用户", description = "用户接口")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private StopWatch stopWatch = new StopWatch();

    @Autowired
    private UserDao userDao;
    @Value("${mob.service.appkey}")
    private String appkey;
    @Value("${mob.service.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "注册")
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity register(@RequestBody UserVo user) {
        if (StringUtils.isEmpty(user.getVerifyCode()) ||
                StringUtils.isEmpty(user.getPassword()) ||
                StringUtils.isEmpty(user.getPhone()) ||
                StringUtils.isEmpty(user.getUsername())) {
            throw new BaseException("参数有误", ResponseType.PARAM_ERRER);
        }
        if (!AccountValidatorUtil.isMobile(user.getPhone())) {
            throw new BaseException("电话号码有误", ResponseType.PARAM_ERRER);
        }
//        if (!verifyCodeCheck(user.getPhone(), user.getVerifyCode())) {
//            throw new BaseException("验证码错误", ResponseType.PARAM_ERRER);
//        }
        UserEntity userEntity = userDao.findUserPhone(user.getPhone());
        if (userEntity != null && userEntity.getId() != null) {
            throw new BaseException("用户已存在", ResponseType.PARAM_ERRER);
        }
        userDao.insertByUser(user);
        log.info("用户注册成功,电话：{}", user.getPhone());
        return SUCCESS;
    }

    @ApiOperation(value = "注销账户")
    @RequestMapping(value = "/{phone}", method = RequestMethod.DELETE)
    ResponseEntity cancellation(@PathVariable(value = "phone") String phone) {
        userDao.deleteByPhone(phone);
        return SUCCESS;
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
        stopWatch.start("开始发送验证码请求");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("appkey", appkey);
        params.add("phone", phone);
        params.add("zone", "86"); //目前只支持86
        params.add("code", verifyCode);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        stopWatch.stop();
        log.info("发送验证码校验请求耗时：{}s，response：{}", stopWatch.getTotalTimeSeconds(), response.getBody());
        Map<String, String> map = new HashMap<>();
        String status = map.get("status");
        return "200".equals(status);
    }

}
