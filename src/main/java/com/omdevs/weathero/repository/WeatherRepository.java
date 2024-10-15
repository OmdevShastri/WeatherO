package com.omdevs.weathero.repository;

import com.omdevs.weathero.entity.Weather;
import com.omdevs.weathero.entity.PincodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByPincodeDetailsAndWeatherDate(PincodeDetails pincodeDetails, LocalDate weatherDate);
}
