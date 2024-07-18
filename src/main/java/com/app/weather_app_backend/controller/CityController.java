package com.app.weather_app_backend.controller;

import com.app.weather_app_backend.model.City;
import com.app.weather_app_backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/find")
    public ResponseEntity<?> getCitiesByName (@RequestParam String search, @RequestParam(required = false) String country) {
        if (search.isEmpty()) {
            return ResponseEntity.badRequest().body("QueryParam 'search' is empty");
        }

        List<City> cities = cityService.getCitiesByName(search);

        if (cities.isEmpty()) {
            return ResponseEntity.ok("Not cities found");
        }

        return ResponseEntity.ok(cities);

    }
}
