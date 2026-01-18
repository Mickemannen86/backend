package com.examensbe.backend.services;

import com.examensbe.backend.models.gasStation.GasStation;
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

    public GasStation processLocation(double latitude, double longitude) throws IOException {
        // Kallar methoden fråm theApp klassen för att processa latitude and longitude
        GasStation selectedGasStation = app.processLatitudeLongitude(latitude, longitude);
        // Return tillbaka till Controller
        return selectedGasStation;
    }
}