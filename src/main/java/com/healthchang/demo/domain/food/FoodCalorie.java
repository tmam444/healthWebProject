package com.healthchang.demo.domain.food;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FoodCalorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column private String type;

    @Column private String name;

    @Column private String category;

    @Column private int ones;

    @Column private double calorie;

    @Column private double protein;

    @Column private double fat;

    @Column private double carbohydrate;

    @Column private double sugar;

    @Column private double dietary_fiber;

    @Column private double calcium;

    @Column private double magnesium;

    @Column private double kalium;

    @Column private double natrium;

    @Column private double cholesterol;

    @Column private double vitamin_B1;

    @Column private double vitamin_B2;

    @Column private double vitamin_C;

    @Builder
    public FoodCalorie (String type, String name, String category, int ones, double calorie, double protein, double fat, double carbohydrate, double sugar, double dietary_fiber, double calcium, double magnesium, double kalium, double natrium, double vitamin_B1, double vitamin_B2, double vitamin_C, double cholesterol){
        this.type = type;
        this.name = name;
        this.category = category;
        this.ones = ones;
        this.calorie = calorie;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.sugar = sugar;
        this.dietary_fiber = dietary_fiber;
        this.calcium = calcium;
        this.magnesium = magnesium;
        this.kalium = kalium;
        this.natrium = natrium;
        this.cholesterol = cholesterol;
        this.vitamin_B1 = vitamin_B1;
        this.vitamin_B2 = vitamin_B2;
        this.vitamin_C = vitamin_C;
    }



}

