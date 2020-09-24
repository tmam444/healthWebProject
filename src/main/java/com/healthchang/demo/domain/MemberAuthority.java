package com.healthchang.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum MemberAuthority implements GrantedAuthority {
//    USER("ROLE_USER"),
//    ADMIN("ROLE_ADMIN");

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    //멤버 변수
    private final String authority;
    private final String title;
}
