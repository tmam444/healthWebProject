package com.healthchang.demo.domain.exercise;

import com.healthchang.demo.domain.member.MemberTable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ExerciseCalories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @Column
    private String type;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column
    @NotNull
    private Double calorie;

    @Column
    private String effect;

    @Column
    private String videoLink;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private MemberTable memberId;

    @Builder
    public ExerciseCalories(Long id, String category, String type, String name, Double calorie, String effect, String videoLink, MemberTable memberId){
        this.id = id;
        this.category = category;
        this.type = type;
        this.name = name;
        this.calorie = calorie;
        this.effect = effect;
        this.videoLink = videoLink;
        this.memberId = memberId;
    }

    public void update(String type, String name, String effect, Double calorie){
        this.type=type;
        this.name=name;
        this.effect=effect;
        this.calorie=calorie;
    }

}
