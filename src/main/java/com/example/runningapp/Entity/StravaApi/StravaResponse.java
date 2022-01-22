package com.example.runningapp.Entity.StravaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StravaResponse {
    @JsonProperty("token_type")
    private String token_type;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_at")
    private Integer expires_at;
    @JsonProperty("expires_in")
    private Integer expires_in;
    @JsonProperty("refresh_token")
    private String refresh_token;
    @JsonProperty("athlete")
    private StravaAthlete athlete;
}
