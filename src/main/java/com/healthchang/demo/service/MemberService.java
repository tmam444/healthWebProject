package com.healthchang.demo.service;

import com.healthchang.demo.domain.Member;
import com.healthchang.demo.domain.MemberAuthority;
import com.healthchang.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public List<Member> findAll() {
        return repository.findAll();
    }

    public Member save(Member member) {
        HashSet<MemberAuthority> a = new HashSet<>();
        a.add(MemberAuthority.USER);
        if(member.getUsername().equals("arahansa@naver.com"))
            a.add(MemberAuthority.ADMIN);
        member.setAuthoritySet(a);
        member.setPassword(encoder.encode(member.getPassword()));
        return repository.save(member);
    }

    public void createDummy() {
        for (int i = 0; i<200; i++){
            Member member = new Member();
            member.setUsername(i + "@naver.com");
            member.setPassword(i + "번째 비밀번호");
            save(member);
        }
    }

    public Page<Member> findAll(Pageable pageable) {
        Page<Member> all = repository.findAll(pageable);
        return all;
    }

    static class UserDetailImpl extends User{
        public UserDetailImpl(Member m){
            super(m.getUsername(), m.getPassword(), m.getAuthoritySet());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
        .map(UserDetailImpl::new)
        .orElseThrow(()-> new UsernameNotFoundException(username));
    }
}
