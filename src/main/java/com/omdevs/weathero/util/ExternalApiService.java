package com.omdevs.weathero.util;

import com.omdevs.weathero.entity.PincodeDetails;
import com.omdevs.weathero.entity.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class ExternalApiService {

    private static final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/zip?zip={zip code},{country code}&appid={apikey}";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    private static final String OPENWEATHER_API_KEY = "cac8930c0bdcd720d0352a8adf9bfb76";

    private RestTemplate restTemplate = new RestTemplate();

    public PincodeDetails getLatLongFromPincode(String pincode) {
        String url = GEOCODING_API_URL + pincode + "&key=" + OPENWEATHER_API_KEY;
        // Make API call using RestTemplate and parse lat/long
        // For simplicity, we'll assume a successful response
        double latitude = ...;  // Extract from response
        double longitude = ...; // Extract from response

        return new PincodeDetails(null, pincode, latitude, longitude);
    }

    public Weather getWeather(double latitude, double longitude, LocalDate date) {
        String url = WEATHER_API_URL.replace("{lat}", String.valueOf(latitude))
                .replace("{lon}", String.valueOf(longitude))
                .replace("{apiKey}", OPENWEATHER_API_KEY);

        // Call OpenWeather API and parse response
        // For simplicity, we'll assume a successful response
        double temperature = ...; // Extract from response
        String description = ...; // Extract from response

        return new Weather(null, null, date, temperature, description);
    }
}
