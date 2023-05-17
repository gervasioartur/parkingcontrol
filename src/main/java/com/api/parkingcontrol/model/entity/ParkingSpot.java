package com.api.parkingcontrol.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name = "t_parking_spot")
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpot implements Serializable {
    private static final long serialVersionUID =  1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String parkingSpotNumber;

    @Column(nullable = false, unique = true, length = 70)
    private String licensePlateCar;

    @Column(nullable = false, length = 70)
    private String brandCar;

    @Column(nullable = false, length = 70)
    private String modelCar;

    @Column(nullable = false, length = 70)
    private String colorCar;

    @Column(nullable = false, length = 130)
    private String responsibleName;

    @Column(nullable = false, length = 30)
    private String apartment;

    @Column(nullable = false, length = 30)
    private String block;

    @Column(nullable = false)
    private LocalDateTime registrationDate;
}

