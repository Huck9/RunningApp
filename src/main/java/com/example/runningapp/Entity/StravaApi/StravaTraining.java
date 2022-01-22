package com.example.runningapp.Entity.StravaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StravaTraining {

    private Long resource_state;
    private StravaAthlete athlete;
    private String name;
    private Float distance;
    private Integer moving_time;
    private Integer elapsed_time;
    private Float total_elevation_gain;
    private String type;
    private String workout_type;
    private Long id;
    private String external_id;
    private Long upload_id;
    private LocalDateTime start_date;
    private LocalDateTime start_date_local;
    private String timezone;
    private Float[] start_latlng;
    private Float[] end_latlng;
    private String location_city;
    private String location_state;
    private Float start_latitude;
    private Float start_longitude;
    private Long achievement_count;
    private Long kudos_count;
    private Long comment_count;
    private Long athlete_count;
    private Long photo_count;
    private StravaMap map;
    private boolean trainer;
    private boolean commute;
    private boolean manual;
    @JsonProperty("private")
    private boolean privateTraining;
    private String visibility;
    private boolean flagged;
    private String gear_id;
    private boolean from_accepted_tag;
    private Long upload_id_str;
    private Float average_speed;
    private Float max_speed;
    private Float average_cadence;
    private Integer average_temp;
    private boolean has_heartrate;
    private Float average_heartrate;
    private Float max_heartrate;
    private boolean heartrate_opt_out;
    private boolean display_hide_heartrate_option;
    private Float elev_high;
    private Float elev_low;
    private Long pr_count;
    private Long total_photo_count;
    private boolean has_kudoed;
    private Float suffer_score;

}
