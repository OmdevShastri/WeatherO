package com.omdevs.weathero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pincode_id", nullable = false)
    private PincodeDetails pincodeDetails;

    @Column(nullable = false)
    private LocalDate weatherDate;

    private double temperature;
    private String description;
}
