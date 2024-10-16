package com.omdevs.weathero.util;

import com.omdevs.weathero.entity.PincodeDetails;
import com.omdevs.weathero.entity.Weather;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import java.time.LocalDate;

@Service
public class ExternalApiService {

    private static final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/zip?zip={zip code},{country code}&appid={apikey}";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    private static final String OPENWEATHER_API_KEY = "cac8930c0bdcd720d0352a8adf9bfb76";

    private RestTemplate restTemplate = new RestTemplate();

    public PincodeDetails getLatLongFromPincode(String pincode) throws JSONException {
        String url = GEOCODING_API_URL.replace("{pincode}", pincode)
                .replace("{apiKey}", OPENWEATHER_API_KEY);

        // Call the API using RestTemplate
        String response = restTemplate.getForObject(url, String.class);

        // Parse the response
        JSONObject jsonObject = new JSONObject(response);
        double latitude = jsonObject.getDouble("lat");
        double longitude = jsonObject.getDouble("lon");

        return new PincodeDetails(null, pincode, latitude, longitude);
    }

    public Weather getWeather(double latitude, double longitude, LocalDate date) throws JSONException {
        String url = WEATHER_API_URL.replace("{lat}", String.valueOf(latitude))
                .replace("{lon}", String.valueOf(longitude))
                .replace("{apiKey}", OPENWEATHER_API_KEY);

        // Make API call
        String response = restTemplate.getForObject(url, String.class);

        // Parse the response
        JSONObject jsonObject = new JSONObject(response);

        // Extract weather details
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        String description = jsonObject.getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");

        return new Weather(null, null, date, temperature, description);
    }

    public Weather getWeather(double latitude, double longitude) throws JSONException {
        String url = WEATHER_API_URL.replace("{lat}", String.valueOf(latitude))
                .replace("{lon}", String.valueOf(longitude))
                .replace("{apiKey}", "YOUR_API_KEY");

        // Make API call
        String response = restTemplate.getForObject(url, String.class);

        // Parse the response
        JSONObject jsonObject = new JSONObject(response);

        // Extract weather details
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        String description = jsonObject.getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");

        // Construct Weather object
        return new Weather(null, null, LocalDate.now(), temperature, description);
    }
}
