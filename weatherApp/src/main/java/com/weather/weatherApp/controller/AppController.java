package com.weather.weatherApp.controller;

import com.weather.weatherApp.model.WeatherDetail;
import com.weather.weatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AppController {

    @Autowired
    private WeatherService weatherService;

    /**
     *Clear mongodb recods in 3 days using batch job
     *
     */
  /*  @Scheduled(cron = "${cron.expression.dbclear}", zone="Asia/Singapore")
    public void evictCacheAtIntervals() {
        weatherService.clearStaleRecords();
    }*/

    @RequestMapping(value="/findForcast", method= RequestMethod.GET)
    public ModelAndView save()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Response");

        List<WeatherDetail> weatherDetail=weatherService.getWeatherDetails();
        modelAndView.addObject("details", weatherDetail);
        return modelAndView;
    }
}
