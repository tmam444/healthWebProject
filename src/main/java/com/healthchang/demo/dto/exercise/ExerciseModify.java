package com.healthchang.demo.dto.exercise;

import com.healthchang.demo.domain.member.MemberTable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExerciseModify {

    private Long id;

    private String type;

    private String name;

    private Double calorie;

    private String effect;

    private MemberTable memberId;

}
