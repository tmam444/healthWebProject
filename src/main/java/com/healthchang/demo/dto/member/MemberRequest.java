package com.healthchang.demo.dto.member;

import com.healthchang.demo.domain.member.MemberTable;
import lombok.Getter;

import java.sql.Date;

@Getter
public class MemberRequest {

    private String email;

    private String password;

    private String name;

    private Date date;

    public MemberTable toEntity(){
        return MemberTable.builder().email(email).name(name).password(password).date(date).build();
    }

}
