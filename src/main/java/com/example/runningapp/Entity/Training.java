package com.example.runningapp.Entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Float distance;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime movingTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    private Float avgSpeed;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime avgPace;
    private Float calories;
    private String workoutType;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Transient
    private boolean isImported;
}
