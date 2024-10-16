package com.omdevs.weathero.controller;

import com.omdevs.weathero.entity.Weather;
import com.omdevs.weathero.service.WeatherService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<Weather> getWeather(@RequestParam("pincode") String pincode,
                                              @RequestParam("for_date") String date) throws JSONException {
        Weather weather = weatherService.getWeather(pincode, LocalDate.parse(date));
        return ResponseEntity.ok(weather);
    }
}
