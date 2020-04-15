package com.healthchang.demo.domain;

import org.springframework.security.core.GrantedAuthority;

public enum MemberAuthority implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    String authority;

    MemberAuthority(String authority) { this.authority = authority; }

    @Override
    public String getAuthority() {
        return authority;
    }
}
