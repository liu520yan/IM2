package com.im2.common.feignClient;

import com.im2.common.entity.UserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by liuyan on 2018/8/23.
 */
@FeignClient("im-user")
public interface UserFeign {
    @RequestMapping(method = RequestMethod.POST)
    void register(@RequestBody UserEntity user);

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    UserEntity findUser(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(method = RequestMethod.GET, value = "/user/username")
    UserEntity findUserByName(@RequestParam("username") String username);
}
