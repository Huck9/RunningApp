package com.example.runningapp.Entity.StravaApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StravaMap {
    private String id;
    private String summary_polyline;
    private Long resource_state;
}
