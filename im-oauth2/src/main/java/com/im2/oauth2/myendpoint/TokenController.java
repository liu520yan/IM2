package com.im2.oauth2.myendpoint;

import com.im2.oauth2.OAuth2AccessTokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * Created by liuyan on 2018/9/19.
 */
@RequestMapping(value = "/customized")
@FrameworkEndpoint
public class TokenController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessTokenWrapper> getAccessToken(Principal principal, @RequestParam
            Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return getResponse(accessToken);
    }

    private ResponseEntity<OAuth2AccessTokenWrapper> getResponse(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        OAuth2AccessTokenWrapper wrapper = new OAuth2AccessTokenWrapper("200", accessToken);
        return new ResponseEntity<>(wrapper, headers, HttpStatus.OK);
    }
}
