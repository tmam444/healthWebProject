package com.healthchang.demo.domain.food;

import com.healthchang.demo.dto.food.FoodSearchListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCalorieRepository extends JpaRepository<FoodCalorie, Long> {

    Page<FoodSearchListDto> findByNameContaining(String name, Pageable pageable);

}
