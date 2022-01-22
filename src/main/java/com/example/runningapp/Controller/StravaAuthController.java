package com.example.runningapp.Controller;

import com.example.runningapp.Service.StravaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RequiredArgsConstructor
@RestController
@RequestMapping("/exchange_token")
@Slf4j
public class StravaAuthController {

    private final StravaService stravaService;

    @GetMapping
    public ModelAndView stravaCode(@RequestParam String code) {
        stravaService.getToken(code);
        return new ModelAndView("redirect:/training/import");
    }

}
