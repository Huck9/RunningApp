package com.example.runningapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SwimRecordsDto {
    private Float sumDistance;
    private LocalTime avgMovingTime;
    private String sumMovingTime;
    private Float sumCalories;
    private Float avgSpeed;
    private LocalTime avgPace;
    private LocalTime m50;
    private LocalTime m100;
    private LocalTime m200;
    private LocalTime m400;
    private LocalTime m800;
    private LocalTime m1500;
}
