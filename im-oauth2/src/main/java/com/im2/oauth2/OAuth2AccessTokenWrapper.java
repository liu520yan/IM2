package com.im2.oauth2;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Created by liuyan on 2018/9/19.
 */
@Data
@AllArgsConstructor
public class OAuth2AccessTokenWrapper {
    private String code;
    private OAuth2AccessToken data;
}
