package com.omdevs.weathero.Entity;


import org.springframework.data.annotation.Id;
import java.time.LocalDate;

@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pincode_id")
    private PincodeDetails pincode;
    private LocalDate weatherDate;
    private double temperature;
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Constructor, getters, and setters
}
