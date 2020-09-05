package com.healthchang.demo.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
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

}
