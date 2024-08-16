package dev.zvaryyka.templateservice.service;

import dev.zvaryyka.templateservice.models.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    public UserInfo getUserInfoByToken() {

        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Map<String, Object> claims = jwt.getClaims();
        return convertToUserInfo(claims);
    }

    private UserInfo convertToUserInfo(Map<String, Object> claims) {
        return new UserInfo(
                claims.get("sub").toString(),
                claims.get("preferred_username").toString(),
                claims.get("email").toString(),
                claims.get("name").toString(),
                claims.get("family_name").toString()
        );

    }
}
