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

        // Call methods from theApp class to process latitude and longitude
        GasStation selectedGasStation = app.processLatitudeLongitude(latitude, longitude);

        // Så här långt får jag med mig svaret till Service!
        System.out.println("\nInnehåller svaret i GasStationService något?: " + selectedGasStation + "\n");

        return selectedGasStation;
    }
}
