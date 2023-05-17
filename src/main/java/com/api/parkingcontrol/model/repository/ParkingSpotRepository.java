package com.api.parkingcontrol.model.repository;

import com.api.parkingcontrol.model.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {
    boolean existsByLicensePlateCar(String licensePlateCar);
    boolean existsByParkingSpotNumber(String spotNumber);
    boolean existsParkingSpotByApartmentAndBlock(String apartment, String block);
}
