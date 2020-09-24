package com.healthchang.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
    private String email;

    @Length(min = 7)
    @NotBlank
    private String password;

    private String name;

    private String picture;

    @CreationTimestamp
    private Date date;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<MemberAuthority> authoritySet = new HashSet<>();

    @Builder
    public MemberTable (String email, String name, String password, String picture, Date date, Set<MemberAuthority> authoritySet){
        this.email = email;
        this.name = name;
        this.password = password;
        this.authoritySet = authoritySet;
        this.picture = picture;
        this.date = date;
    }

    public MemberTable update(String name, String email){
        this.name = name;
        this.email = email;
        return this;
    }

}
