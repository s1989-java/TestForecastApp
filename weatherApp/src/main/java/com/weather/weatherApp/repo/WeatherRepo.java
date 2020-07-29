package com.weather.weatherApp.repo;

import com.weather.weatherApp.model.WeatherDetail;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

public interface WeatherRepo {

    List<WeatherDetail> findAll(String timestamp);
    WeatherDetail saveDetail(WeatherDetail detail);
    void deleteDetail(String detail);
    Mono<WeatherDetail> findByLocation(String latitude, String longitude);
    //Mono<WeatherDetail> deleteDetails(Date date);
}
