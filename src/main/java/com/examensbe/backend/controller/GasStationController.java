package com.examensbe.backend.controller;


import com.examensbe.backend.models.user.LocationRequest;
import com.examensbe.backend.models.geoCodeReverse.Geometry;

import com.examensbe.backend.services.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/location")
public class GasStationController {


    private final GasStationService gasStationService;

    @Autowired
    public GasStationController(GasStationService gasStationService) {
        this.gasStationService = gasStationService;

    }

    @PostMapping("/coordinateData") // localhost:3000/location/coordinateData
    public ResponseEntity<?> getGasStations(@RequestBody LocationRequest locationRequest) throws IOException {
        // Call the service method to process the location data
        Geometry response = gasStationService.processLocation(locationRequest.latitude(), locationRequest.longitude());

        // Skriv ut sparad Location
        System.out.println("Lat: " + response.getLocation().getLat() +
                "\nLong: " + response.getLocation().getLng());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
