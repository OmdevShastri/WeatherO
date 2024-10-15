package com.omdevs.weathero.service;

import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public LatLong getLatLongFromPincode(String pincode) {
        // Use Google Maps or OpenWeather Geocoding API to get lat/long
    }

    public Weather getWeather(double lat, double lon, String date) {
        // Use OpenWeather API to fetch weather data
    }
}
