package com.healthchang.demo.repository;

import com.healthchang.demo.domain.ExerciseCalories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseCalories, Long> {

    List<ExerciseCalories> findAllByOrderByName();
    List<ExerciseCalories> findAllByOrderByCalorie();
    List<ExerciseCalories> findAllByOrderByCalorieDesc();

}
