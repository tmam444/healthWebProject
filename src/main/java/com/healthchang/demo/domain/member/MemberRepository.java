package com.healthchang.demo.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberTable, Long> {
    Optional<MemberTable> findByEmail(String email);
    MemberTable findByEmailEndingWith(String email);

}
