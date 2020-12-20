package com.healthchang.demo.service;

import com.healthchang.demo.domain.member.MemberAuthority;
import com.healthchang.demo.domain.member.MemberRepository;
import com.healthchang.demo.domain.member.MemberTable;
import com.healthchang.demo.dto.member.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository repository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public boolean save(MemberRequest member) {
        boolean checkMember = repository.findByEmail(member.getEmail()).orElse(null) == null ? true : false;
        if(checkMember){
            HashSet<MemberAuthority> authorities = new HashSet<>();
            authorities.add(MemberAuthority.USER);
            String password = encoder.encode(member.getPassword());
            repository.save(MemberTable.builder()
                                .email(member.getEmail())
                                .name(member.getName())
                                .date(member.getDate())
                                .password(password)
                                .authoritySet(authorities)
                            .build());
            return true;
        }
        return false;
    }

    public boolean checkDuplicationId(String id){
        return repository.findByEmail(id).orElse(null) == null ? true : false;
    }

    public MemberTable findByEmailEndingWith(String name) {
        return repository.findByEmailEndingWith(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(UserDetailImpl::new)
                .orElseThrow(()-> new UsernameNotFoundException(username));
    }

    public static class UserDetailImpl extends User{

        private String memberName;

        public UserDetailImpl(MemberTable m){
            super(m.getEmail(), m.getPassword(), m.getAuthoritySet());
            this.memberName = m.getName();
        }

        public String getName(){
            return this.getUsername();
        }

        public String getMemberName(){
            return this.memberName;
        }

    }

}
