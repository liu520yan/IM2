package com.im2.oauth2.config;


import com.im2.common.entity.UserEntity;
import com.im2.common.feignClient.Oauth2Feign;
import com.im2.common.feignClient.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by liuyan on 2018/8/23.
 */

@Service
public class UserDetail implements UserDetailsService {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private Oauth2Feign oauth2Feign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        userEntity = userFeign.findUserByName(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return new IMUserDetails(userEntity);
    }
}
