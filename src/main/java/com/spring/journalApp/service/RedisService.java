package com.spring.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.journalApp.api.Response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T get(String key , Class<T> entityClass){
       try{
           Object o = redisTemplate.opsForValue().get(key);
           if (o == null) {
               log.warn("Cache miss for key: {}", key);
               return null;
           }
           ObjectMapper mapper = new ObjectMapper();
           return mapper.readValue(o.toString(), entityClass);
       }
       catch(Exception e){
            log.error("Exception ",e);
            return null;
       }
    }
    public void set(String key ,Object o , Long ttl){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key , json , ttl , TimeUnit.SECONDS);

        }
        catch(Exception e){
            log.error("Exception ",e);
        }
    }
}
