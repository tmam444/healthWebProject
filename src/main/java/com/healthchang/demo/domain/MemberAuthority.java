package com.healthchang.demo.domain;

import org.springframework.security.core.GrantedAuthority;

public enum MemberAuthority implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    //멤버 변수
    String authority;

    //생성자
    MemberAuthority(String authority) { this.authority = authority; }

    //getter
    @Override
    public String getAuthority() {
        return authority;
    }
}
