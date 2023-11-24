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
public class RecordsDto {
    private Float sumDistance;
    private LocalTime avgMovingTime;
    private String sumMovingTime;
    private Float sumCalories;
    private Float avgSpeed;
    private LocalTime avgPace;
    private LocalTime fivekm;
    private LocalTime tenkm;
    private LocalTime halfmarathon;
    private LocalTime marathon;
}
