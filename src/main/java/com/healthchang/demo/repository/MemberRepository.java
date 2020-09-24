package com.healthchang.demo.repository;

import com.healthchang.demo.domain.MemberTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberTable, Long> {
    Optional<MemberTable> findByEmail(String username);
}
