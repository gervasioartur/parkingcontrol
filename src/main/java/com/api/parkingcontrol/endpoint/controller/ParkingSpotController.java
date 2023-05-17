package com.api.parkingcontrol.endpoint.controller;

import com.api.parkingcontrol.endpoint.services.ParkingSpotService;
import com.api.parkingcontrol.model.dto.ParkingSpotDto;
import com.api.parkingcontrol.model.entity.ParkingSpot;
import com.sun.source.tree.OpensTree;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: Lincense plate car already in use");

        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: Parking spot number already in use");

        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: Parking spot already registered to this apartment/block");

        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpot));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotService.findById(id);
        if(! parkingSpotOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotService.findById(id);
        if(! parkingSpotOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        parkingSpotService.delete(parkingSpotOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("parking spot deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        Optional<ParkingSpot> parkingSpotOptional = parkingSpotService.findById(id);
        if(! parkingSpotOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found");
        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto,parkingSpot);
        parkingSpot.setId(parkingSpotOptional.get().getId());
        parkingSpot.setRegistrationDate(parkingSpotOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpot));
    }
}
