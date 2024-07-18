package com.app.weather_app_backend.service;

import com.app.weather_app_backend.model.City;
import com.app.weather_app_backend.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getCitiesByName (String name) {
        return cityRepository.findCityByName(name);
    }

    public List<City> getCitiesByNameAndCountry (String nameCity, String codeCountry) {
        return cityRepository.findCityByNameAndCountry(nameCity, codeCountry);
    }
}
