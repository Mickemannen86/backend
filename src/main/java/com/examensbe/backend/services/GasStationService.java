package com.examensbe.backend.services;

import com.examensbe.backend.models.geoCodeReverse.Geometry;
import com.examensbe.backend.models.geoCodeReverse.Location;
import com.examensbe.backend.myApp.theApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GasStationService {

    private final theApp app;

    @Autowired
    public GasStationService(theApp app) {
        this.app = app;
    }

    public Geometry processLocation(double latitude, double longitude) throws IOException {

        Geometry savedLocationslatLong = new Geometry();
        Location location = new Location();

        location.setLat(latitude);
        location.setLng(longitude);
        savedLocationslatLong.setLocation(location);

        // Call methods from theApp class to process latitude and longitude
        app.processLatitudeLongitude(latitude, longitude);

        return savedLocationslatLong;
    }
}
