package com.example.runningapp.Entity.StravaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StravaAthlete {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("resource_state")
    private Long resource_state;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("premium")
    private boolean premium;
    @JsonProperty("summit")
    private boolean summit;
    @JsonProperty("created_at")
    private LocalDateTime created_at;
    @JsonProperty("updated_at")
    private LocalDateTime updated_at;
    @JsonProperty("badge_type_id")
    private Long badge_type_id;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("profile_medium")
    private String profile_medium;
    @JsonProperty("profile")
    private String profile;
    @JsonProperty("friend")
    private String friend;
    @JsonProperty("follower")
    private String follower;

}
