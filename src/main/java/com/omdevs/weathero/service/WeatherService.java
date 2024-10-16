package com.omdevs.weathero.service;
import com.omdevs.weathero.entity.LatLong;
import com.omdevs.weathero.entity.PincodeDetails;
import com.omdevs.weathero.entity.Weather;
import com.omdevs.weathero.repository.PincodeRepository;
import com.omdevs.weathero.repository.WeatherRepository;
import com.omdevs.weathero.util.ExternalApiService;
import org.json.JSONException;
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

    public Weather getWeather(String pincode, LocalDate date) throws JSONException {

        // Step 1: Check if Pincode exists in DB
        Optional<PincodeDetails> pincodeDetailsOpt = pincodeRepository.findByPincode(pincode);
        if (!pincodeDetailsOpt.isPresent()) {
            // Fetch lat/long from external API
            PincodeDetails latLong = externalApiService.getLatLongFromPincode(pincode);
            latLong = new PincodeDetails(pincode, latLong.getLatitude(), latLong.getLongitude());
            pincodeRepository.save(pincodeDetailsOpt.get());
        }

        PincodeDetails pincodeDetails = pincodeDetailsOpt.get();

        // Step 2: Check if weather data for the requested date is in DB
        Optional<Weather> weatherOpt = weatherRepository.findByPincodeDetailsAndWeatherDate(pincodeDetails, date);
        if (weatherOpt.isPresent()) {
            return weatherOpt.get(); // Return the weather details from the database
        }

        // Step 3: If the requested date is the current date, fetch current weather from API
        if (date.equals(LocalDate.now())) {
            Weather weather = externalApiService.getWeather(pincodeDetails.getLatitude(), pincodeDetails.getLongitude());
            weather.setPincodeDetails(pincodeDetails);
            weather.setWeatherDate(date);
            weatherRepository.save(weather); // Save the weather to the database
            return weather;
        } else {
            // If it's an older date, return a message saying data is not available
            throw new RuntimeException("Weather data for " + date + " is not available.");
        }
    }
}
