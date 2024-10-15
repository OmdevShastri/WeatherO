package com.omdevs.weathero.service;
import com.omdevs.weathero.entity.PincodeDetails;
import com.omdevs.weathero.entity.Weather;
import com.omdevs.weathero.repository.PincodeRepository;
import com.omdevs.weathero.repository.WeatherRepository;
import com.omdevs.weathero.util.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private ExternalApiService externalApiService;

    public Weather getWeather(String pincode, LocalDate date) {
        // Check if Pincode exists in DB
        Optional<PincodeDetails> pincodeDetailsOpt = pincodeRepository.findByPincode(pincode);
        PincodeDetails pincodeDetails;

        if (pincodeDetailsOpt.isPresent()) {
            pincodeDetails = pincodeDetailsOpt.get();
        } else {
            // Fetch Lat/Long via External API and save
            pincodeDetails = externalApiService.getLatLongFromPincode(pincode);
            pincodeRepository.save(pincodeDetails);
        }

        // Check if weather data exists for the date
        Optional<Weather> weatherOpt = weatherRepository.findByPincodeDetailsAndWeatherDate(pincodeDetails, date);
        if (weatherOpt.isPresent()) {
            return weatherOpt.get();
        }

        // Fetch weather via External API
        Weather weather = externalApiService.getWeather(pincodeDetails.getLatitude(), pincodeDetails.getLongitude(), date);
        weatherRepository.save(weather);

        return weather;
    }
}
