package com.omdevs.weathero;

import com.omdevs.weathero.entity.PincodeDetails;
import com.omdevs.weathero.entity.Weather;
import com.omdevs.weathero.repository.PincodeRepository;
import com.omdevs.weathero.repository.WeatherRepository;
import com.omdevs.weathero.service.WeatherService;
import com.omdevs.weathero.util.ExternalApiService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherServiceTest {

    @Mock
    private PincodeRepository pincodeRepository;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private ExternalApiService externalApiService;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void testGetWeatherFromAPI() throws JSONException {
        String pincode = "410206";
        LocalDate date = LocalDate.of(2020, 10, 15);

        PincodeDetails pincodeDetails = new PincodeDetails(1L, pincode, 18.5204, 73.8567);
        Weather expectedWeather = new Weather(1L, pincodeDetails, date, 302.16, "Clouds");

        // Mock repository and external API calls
        when(pincodeRepository.findByPincode(pincode)).thenReturn(Optional.of(pincodeDetails));
        when(weatherRepository.findByPincodeDetailsAndWeatherDate(pincodeDetails, date)).thenReturn(Optional.empty());
        when(externalApiService.getWeather(18.5204, 73.8567, date)).thenReturn(expectedWeather);

        // Call service and assert the result
        Weather actualWeather = weatherService.getWeather(pincode, date);
        assertEquals(expectedWeather, actualWeather);
    }
    @Test
    public void testGetWeatherForOldDate() {
        String pincode = "411014";
        LocalDate oldDate = LocalDate.of(2020, 10, 15);
        PincodeDetails pincodeDetails = new PincodeDetails(1L, pincode, 18.5204, 73.8567);

        // Mock repository to return pincode details
        when(pincodeRepository.findByPincode(pincode)).thenReturn(Optional.of(pincodeDetails));

        // Mock repository to return no weather for the old date
        when(weatherRepository.findByPincodeDetailsAndWeatherDate(pincodeDetails, oldDate)).thenReturn(Optional.empty());

        // Call service and expect exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getWeather(pincode, oldDate);
        });

        assertEquals("Weather data for 2020-10-15 is not available.", exception.getMessage());
    }

}
