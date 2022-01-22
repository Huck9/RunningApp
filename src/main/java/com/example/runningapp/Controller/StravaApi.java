package com.example.runningapp.Controller;

import com.example.runningapp.Service.StravaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RequiredArgsConstructor
@RestController
@RequestMapping("/strava")
@Slf4j
public class StravaApi {

    private final StravaService stravaService;

    @GetMapping("/code")
    public RedirectView stravaCode() {
        return new RedirectView(stravaService.getCodeRedirectUrl());
    }


}
