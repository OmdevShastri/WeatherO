package com.omdevs.weathero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private ExternalApiService externalApiService;

    public WeatherResponse getWeather(String pincode, String date) {
        PincodeDetails pincodeDetails = pincodeRepository.findByPincode(pincode);

        if (pincodeDetails == null) {
            // Fetch lat/long from external API
            LatLong latLong = externalApiService.getLatLongFromPincode(pincode);
            pincodeDetails = new PincodeDetails(pincode, latLong.getLatitude(), latLong.getLongitude());
            pincodeRepository.save(pincodeDetails);
        }

        Weather weather = weatherRepository.findByPincodeAndDate(pincodeDetails.getId(), date);

        if (weather == null) {
            // Fetch weather info from external API
            weather = externalApiService.getWeather(pincodeDetails.getLatitude(), pincodeDetails.getLongitude(), date);
            weatherRepository.save(weather);
        }

        return new WeatherResponse(weather);
    }
}
