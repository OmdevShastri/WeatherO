package com.omdevs.weathero.repository;

import com.omdevs.weathero.entity.PincodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PincodeRepository extends JpaRepository<PincodeDetails, Long> {
    Optional<PincodeDetails> findByPincode(String pincode);
}
