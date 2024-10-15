package com.omdevs.weathero;

import com.omdevs.weathero.entity.PincodeDetails;
import com.omdevs.weathero.entity.Weather;
import com.omdevs.weathero.repository.PincodeRepository;
import com.omdevs.weathero.repository.WeatherRepository;
import com.omdevs.weathero.service.WeatherService;
import com.omdevs.weathero.util.ExternalApiService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetWeatherFromAPI() {
        String pincode = "411014";
        LocalDate date = LocalDate.of(2020, 10, 15);

        PincodeDetails pincodeDetails = new PincodeDetails(1L, pincode, 18.5204, 73.8567);
        Weather expectedWeather = new Weather(1L, pincodeDetails, date, 30.5, "Clear Sky");

        // Mock repository and external API calls
        when(pincodeRepository.findByPincode(pincode)).thenReturn(Optional.of(pincodeDetails));
        when(weatherRepository.findByPincodeDetailsAndWeatherDate(pincodeDetails, date)).thenReturn(Optional.empty());
        when(externalApiService.getWeather(18.5204, 73.8567, date)).thenReturn(expectedWeather);

        // Call service and assert the result
        Weather actualWeather = weatherService.getWeather(pincode, date);
        assertEquals(expectedWeather, actualWeather);
    }
}
