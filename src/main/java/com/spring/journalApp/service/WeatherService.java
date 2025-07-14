package com.spring.journalApp.service;

import com.spring.journalApp.Cache.appCache;
import com.spring.journalApp.api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;
    @Autowired
    private appCache Appcache;
    @Autowired
    private RestTemplate restTemplate;
    
    public WeatherResponse getweather(String city) {
        String template = Appcache.App_Cache.get(appCache.keys.WEATHER_API.toString());
        if (template == null) {
            // Use a default template if the key is not found in cache
            template = "http://api.weatherapi.com/v1/current.json?key=<api_key>&q=<city>&aqi=no";
            System.out.println("Using default weather API template");
        }
        
        String finalapi = template
                .replace("<city>", city)
                .replace("<api_key>", apikey);

        try {
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                    finalapi, HttpMethod.GET, null, WeatherResponse.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error calling weather API: " + e.getMessage());
            return null;
        }
    }
}
