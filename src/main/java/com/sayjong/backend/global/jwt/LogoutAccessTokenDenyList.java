package com.sayjong.backend.global.jwt;

import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class LogoutAccessTokenDenyList {

    private final Set<String> denyList = Collections.newSetFromMap(new ConcurrentHashMap<>());

    //로그아웃 된 토큰을 denyList에 추가
    public void add(String accessToken) {
        denyList.add(accessToken);
    }

    //토큰이 denyList에 있는지 확인
    public boolean contains(String accessToken) {
        return denyList.contains(accessToken);
    }
}
