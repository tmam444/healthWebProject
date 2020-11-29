package com.healthchang.demo.dto.exercise;

import com.healthchang.demo.domain.member.MemberTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequest {

    private String type;

    private String name;

    private Double calorie;

    private String effect;

    private MemberTable memberId;

}
