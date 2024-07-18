package com.app.weather_app_backend.repository;

import com.app.weather_app_backend.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CityRepository extends MongoRepository<City, Long> {

    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<City> findCityByName(String name);

    @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'country': ?1 }")
    List<City> findCityByNameAndCountry(String nameCity, String codeCountry);

}
