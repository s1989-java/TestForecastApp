package com.weather.weatherApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherApp.model.ApplicationParams;
import com.weather.weatherApp.model.Countries;
import com.weather.weatherApp.model.WeatherDetail;
import com.weather.weatherApp.repo.WeatherRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeatherService {

    private static final Logger LOGGER = LogManager.getLogger(WeatherService.class);

    @Autowired
    private ApplicationParams applicationParams;

    @Autowired
    private WeatherRepo weatherRepo;

    @Autowired
    private RestTemplate restTemplate;

    public List<WeatherDetail> getWeatherDetails(){
        LOGGER.debug("get list of weather records");
        LOGGER.info("I am using location coordinates directly, could also use some api that could" +
                "return coordinates based on country code, Also could use a json or csv file");
        List<Countries> countries= new ArrayList<>();
        countries.add(new Countries("Omaha","41.256538","-95.934502"));
        countries.add(new Countries("Campbell","37.287659","-121.942429"));
        countries.add(new Countries("Niseko","10.931650","-3.422980"));
        countries.add(new Countries("Austin","30.267153","-97.743057"));
        countries.add(new Countries("Nara","0.000000","0.000000"));
        countries.add(new Countries("Jakarta","0.000000","0.000000"));
        List<WeatherDetail> weatherDetails= new ArrayList<>();
        for(Countries country:countries){
            WeatherDetail weatherDetail= (WeatherDetail) getWeatherByCountry(country.getLatitude(),country.getLongitude());
            weatherDetails.add(weatherDetail);
        }
        return  weatherDetails;
    }

    public Object getWeatherByCountry(String latitude, String longitude){
        LOGGER.debug("Get weather details by latitude and Longitude using dark sky");
        if(StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)){
            LOGGER.error("lat long params are missing");
            throw new RuntimeException("Please provide valid parameters for processing");
        }
        LOGGER.debug("Vaidate if details exist in db using lat and long ");
        Mono<WeatherDetail> weatherDetailexists=weatherRepo.findByLocation(latitude,longitude);
        if(null!=weatherDetailexists){
            LOGGER.debug("Return if record exist otherwise proceed to make a call to api  ");
            return  weatherDetailexists;
        }
        StringBuilder sb = new StringBuilder(applicationParams.getUrl());
        sb.append(applicationParams.getKey()+"/");
        sb.append(latitude+","+longitude);
        Object resp= restTemplate.getForObject(sb.toString(), Object.class);
        WeatherDetail weatherDetail=convertJsonToObject(toJsonString(resp),WeatherDetail.class);
        weatherRepo.saveDetail(weatherDetail);
        return  weatherDetail;
    }

    public static <T> T convertJsonToObject(String stringJson, Class<T> classType) {
        T object = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            object = mapper.readValue(stringJson, classType);
        } catch (IOException exception) {
            LOGGER.error("exception occured in convertJsonToObject : {}",exception);
        }
        return object;
    }

    public static String toJsonString(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String res = null;
        try{
            res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }catch (JsonProcessingException exec) {
            LOGGER.error("exception occured in toJson :{}",exec);
        }
        return res;
    }

    /*public void clearStaleRecords() {
        weatherRepo.deleteDetails(new Date());
    }*/
}
