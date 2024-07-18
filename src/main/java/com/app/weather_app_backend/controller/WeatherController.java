package com.app.weather_app_backend.controller;

import com.app.weather_app_backend.exceptions.ResourceNotFoundException;
import com.app.weather_app_backend.service.WeatherService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weather.api.key}")
    private String WEATHER_API_KEY;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    private ResponseEntity<Object> getWeather(@RequestParam(required = false) Integer cityId) {
        Map<String, Object> body = new HashMap<>();
        if (cityId == null) {
            body.put("message", "cityId parameter is missing");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(weatherService.getWeatherApiResponse(cityId, WEATHER_API_KEY), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            body.put("message", e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

}
