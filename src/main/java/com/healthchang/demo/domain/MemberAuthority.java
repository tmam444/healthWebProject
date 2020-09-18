package com.healthchang.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum MemberAuthority implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    //멤버 변수
    private final String authority;
}
