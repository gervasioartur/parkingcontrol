package com.api.parkingcontrol.endpoint.services;

import com.api.parkingcontrol.model.entity.ParkingSpot;
import com.api.parkingcontrol.model.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return this.parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String spotNumber) {
        return this.parkingSpotRepository.existsByParkingSpotNumber(spotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return this.parkingSpotRepository.existsParkingSpotByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpot> findAll() {
        return this.parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpot> findById(UUID id) {
        return  this.parkingSpotRepository.findById(id);
    }

    public void delete(ParkingSpot parkingSpot) {
        this.parkingSpotRepository.delete(parkingSpot);
    }
}
