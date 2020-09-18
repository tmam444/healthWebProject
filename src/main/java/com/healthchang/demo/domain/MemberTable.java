package com.healthchang.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class MemberTable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 2)
    @Email
    @Column(unique = true)
    @NotBlank
    private String username;

    @Length(min = 7)
    @NotBlank
    private String password;

    @NotBlank
    private String name;
    
    private Date date;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<MemberAuthority> authoritySet = new HashSet<>();

    @Builder
    public MemberTable (String username, String name, String password,Set<MemberAuthority> authoritySet){
        this.username = username;
        this.name = name;
        this.password = password;
        this.authoritySet = authoritySet;
    }

    public MemberTable update(String name, String email){
        this.name = name;
        this.username = email;
        return this;
    }

}
