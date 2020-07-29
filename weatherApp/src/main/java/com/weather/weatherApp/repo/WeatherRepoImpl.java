package com.weather.weatherApp.repo;

import com.weather.weatherApp.model.WeatherDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Repository
public class WeatherRepoImpl implements WeatherRepo{

    @Autowired
    private final ReactiveMongoTemplate mongoTemplate;

    public WeatherRepoImpl(ReactiveMongoTemplate  mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<WeatherDetail> findAll(String timestamp) {
        return (List<WeatherDetail>) mongoTemplate.findAll(WeatherDetail.class);
    }

    @Override
    public WeatherDetail saveDetail(WeatherDetail detail) {
        mongoTemplate.save(detail);
        return detail;
    }

    @Override
    public void deleteDetail(String detail) {
        mongoTemplate.remove(detail);
    }

    @Override
    public Mono<WeatherDetail> findByLocation(String latitude, String longitude) {
        Query query = new Query();
        query.addCriteria(Criteria.where("latitude").gt(latitude)
                .andOperator(Criteria.where("longitude").lt(longitude)));
        return mongoTemplate.findOne(query, WeatherDetail.class);
    }

   /* @Override
    public Mono<WeatherDetail> deleteDetails(Date date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("date").is(date));

        return mongoTemplate.findAndRemove(query, WeatherDetail.class);
    }*/
}
