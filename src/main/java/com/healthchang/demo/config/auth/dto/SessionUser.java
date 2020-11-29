package com.healthchang.demo.config.auth.dto;

import com.healthchang.demo.domain.member.MemberTable;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(MemberTable user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
