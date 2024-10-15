package com.omdevs.weathero.Entity;

import org.springframework.data.annotation.Id;

@Entity
public class PincodeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private double latitude;
    private double longitude;

    // Constructor, getters, and setters
}
