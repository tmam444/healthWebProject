package com.healthchang.demo.domain.healthSupplement;

import com.healthchang.demo.dto.healthSupplement.HealthSupplementDto;
import com.healthchang.demo.dto.healthSupplement.HealthSupplementTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HealthSupplementRepository extends JpaRepository<HealthSupplement, Long> {

    List<HealthSupplementDto> findAllBy();

    @Query(value = "select type as type from HealthSupplement group by type")
    List<HealthSupplementTypeDto> findGroupByType();

}
