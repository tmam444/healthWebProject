package com.healthchang.demo.dto.exercise;

import com.healthchang.demo.domain.member.MemberTable;

public interface ExerciseCaloriesListDto {
    Long getId();
    String getCategory();
    String getType();
    String getName();
    Double getCalorie();
    String getEffect();
    String getVideoLink();
    MemberTable getMemberId();
}
