package com.app.weather_app_backend.controller;

import com.app.weather_app_backend.exceptions.ResourceNotFoundException;
import com.app.weather_app_backend.model.City;
import com.app.weather_app_backend.service.CityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/city")
public class CityController {

    @Value("${ipinfo.api.key}")
    private String IP_INFO_API_KEY;

    @Value("${ip.test}")
    private String defaultIp;

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

    @GetMapping("/local")
    public ResponseEntity<?> getLocationCity (HttpServletRequest req) {
        String clientIp = req.getHeader("X-Forwarded-For");

        if (clientIp ==  null) {
            clientIp = req.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(clientIp) || "::1".equals(clientIp) || "127.0.0.1".equals(clientIp)) {
            clientIp = defaultIp;
        }

        try {
            return new ResponseEntity<>(cityService.getLocalCityInfo(clientIp, IP_INFO_API_KEY), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }
}
