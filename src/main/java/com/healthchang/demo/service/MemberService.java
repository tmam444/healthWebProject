package com.healthchang.demo.service;

import com.healthchang.demo.domain.MemberAuthority;
import com.healthchang.demo.domain.MemberTable;
import com.healthchang.demo.repository.MemberRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public List<MemberTable> findAll() {
        return repository.findAll();
    }

    public MemberTable save(MemberTable member) {
        MemberTable memberTable = repository.findByUsername(member.getUsername()).orElse(null);
        if(memberTable == null){
            HashSet<MemberAuthority> a = new HashSet<>();
            a.add(MemberAuthority.USER);
            member.setAuthoritySet(a);
            member.setPassword(encoder.encode(member.getPassword()));
            return repository.save(member);
        }else{
            return null;
        }
    }

    public Page<MemberTable> findAll(Pageable pageable) {
        Page<MemberTable> all = repository.findAll(pageable);
        return all;
    }

    static class UserDetailImpl extends User{
        public UserDetailImpl(MemberTable m){
            super(m.getUsername(), m.getPassword(), m.getAuthoritySet());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
        .map(UserDetailImpl::new)
        .orElseThrow(()-> new UsernameNotFoundException(username));
    }

    public boolean checkDuplicationId(String id){
        MemberTable memberTable = repository.findByUsername(id).orElse(null);
        if(memberTable == null){
            return true;
        }
        return false;
    }

}
