package com.examensbe.backend.myApp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.examensbe.backend.models.gasStation.GasStation;
import com.examensbe.backend.models.geoCodeReverse.AddressComponent;
import com.examensbe.backend.models.geoCodeReverse.GeoCodeReverse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class theApp {

    @Value("${GeoCodeKey}")
    private String YOUR_API_KEY;

    private final ObjectMapper objectMapper;

    public theApp(ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
    }


    // Method to fetch latitude and longitude from the frontend
    public void fetchLatLong(double latitude, double longitude) {
        // Your implementation to handle latitude and longitude data from the frontend

        // Hämta lat long object (frontend)
        System.out.println("myAppCalculations: " + latitude + " " + longitude);

    }

    // Method to construct URL for geocoding reverse
    public String constructGeocodingURL(double latitude, double longitude) {
        // Your implementation to construct the geocoding reverse URL
        String baseURL = "https://maps.googleapis.com/maps/api/geocode/json";
        String apiKey = YOUR_API_KEY; // Replace with your actual API key
        String url = baseURL + "?latlng=" + latitude + "," + longitude + "&key=" + apiKey; // skydda min key i application.properties
        return url;
    }

    public String reverseGeoCoding(String GeoCodingURL) throws IOException {


        try {
            // objectMapper.ReadValue för att köra anropet
            GeoCodeReverse geoCodingResponse = objectMapper.readValue(new URL(GeoCodingURL), GeoCodeReverse.class);

            System.out.println("Kommer vi hit?!");
            System.out.println(geoCodingResponse.getResults().size());
            System.out.println("Ovan finns objektet?");

            // Itererar igenom address componenterna för att hitta postal_town
            for (AddressComponent addressComponent : geoCodingResponse.getResults().get(0).getAddressComponents()) {

                System.out.println("Innanför For loopen!");

                if (addressComponent.getTypes().contains("postal_town")) {

                    System.out.println("Innanför if satsen om postal_town!");

                    System.out.println("Postal Town: " + addressComponent.getLongName());
                    // You can store or process the postal town data as needed
                    return addressComponent.getLongName(); // Assuming you only want the first postal town found
                }
            }
            System.out.println("Postal town not found.");
            return null; // Returnerar null om postal town inte hittas
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching geocoding data: " + e.getMessage());
            return null; // Returnerar null om ett fel uppstår
        }
    }

    // Method to extract address from geocoding result
    public String extractAddress(String geocodingResult) {

        // ta ut adress värde Tyresö/Bollmora

        // Your implementation to extract address from geocoding result
        // Use Jackson ObjectMapper to parse JSON response
        return null;
    }

    // Method to query fakeStations.json for list of gas stations
    public List<GasStation> queryGasStations(String postal_town) throws IOException {

        System.out.println("postal_town värdet kommer in: " + postal_town);

        // Load data from fakeStations.json
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("fakeStations.json");
        List<GasStation> gasStations = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        System.out.println("gasStations Size: " + gasStations.size());
        System.out.println("gasStations object: " + gasStations);

        // Filter gas stations by the given postal town
        List<GasStation> filteredGasStations = gasStations.stream()
                .filter(station -> station.getPostalTown() != null && station.getPostalTown().equalsIgnoreCase(postal_town))
                .collect(Collectors.toList());

        System.out.println("filteredGasStations Size: " + filteredGasStations.size());
        System.out.println("filteredGasStations object: " + filteredGasStations);

        return filteredGasStations;
    }

    // Method to select gas station with the lowest price

    public String selectGasStationWithLowestPrice(List<GasStation> gasStations) throws IOException {
        // Find the gas station with the lowest price
        Optional<GasStation> cheapestGasStation = gasStations.stream()
                .min(Comparator.comparingDouble(station -> parsePrice(station.getPrice())));

        // If a cheapest gas station is found, return its details
        if (cheapestGasStation.isPresent()) {
            GasStation cheapestStation = cheapestGasStation.get();
            return "\n-- Cheapest Gas Station --" +
                    "\nGas Station: " + cheapestStation.getGasStation() +
                    "\nFuel: " + cheapestStation.getFuel() +
                    "\nPrice: " + cheapestStation.getPrice() +
                    "\nAddress: " + cheapestStation.getAddress() +
                    "\nStreet Number :" + cheapestStation.getStreetNr() +
                    "\nPostal Code: " + cheapestStation.getPostalCode() +
                    "\nPostal Town: " + cheapestStation.getPostalTown() +
                    "\nSublocality: " + cheapestStation.getSublocality() +
                    "\n--------------------------";
        } else {
            // If no gas station is found, return a message indicating so
            return "No gas station found.";
        }
    }

    // Helper method to parse price string to double
    private double parsePrice(String price) {
        // Remove non-numeric characters, replace comma with dot, and parse to double
        return Double.parseDouble(price.replaceAll("[^\\d.,]", "").replace(",", "."));
    }

    // Method to process latitude and longitude
    public void processLatitudeLongitude(double latitude, double longitude) throws IOException {
        // Anropar fetchLatLong för att hantera latitud och longitud från frontend.
        fetchLatLong(latitude, longitude); // Få notis om att lat lng nått theApp från frontend

        // Skapar en URL för geocoding med constructGeocodingURL och sätter in latitud och longitud i den.
        String geocodingURL = constructGeocodingURL(latitude, longitude); // Skapa anrop URL sätt in värden öat lng

        // Hämtar ut postal_town från min ReverseGeocode response
        String postalTownResult = reverseGeoCoding(geocodingURL);

        // Frågar fakeStations.json efter bensinstationer baserat på den postal_town värdet returnerat av ReverseGeoCoding()
        List<GasStation> gasStations = queryGasStations(postalTownResult);

        // Väljer den bensinstationen med lägsta priset från resultatet med selectGasStationWithLowestPrice.
        String selectedGasStation = selectGasStationWithLowestPrice(gasStations);
        // Return selected gas station data to the controller or further process it
        System.out.println("Slutresultat Check: " + selectedGasStation);
    }

    // Hämta lat long object (frontend)                                                                                 -- 1.) klart!


    // sätt in lat long värden i URL för geCoding reverse                                                               -- 2.) klart!


    // hämta geCoding reverse response & ta ut address värde postal_town som tex Tyresö baserat på lat/lng              -- 3.) klart


    // sätt in address värde postal_town som tex Tyresö i selectGasStationWithLowestPrice()
    // och ta ut lista med bensinstationer(gasStations mm.) från fakeStations.json response payloaden                   -- 4.) klart


    // ta ut station baserat på lägst pris                                                                              -- 5.) klart


    // Returnera Station, adress & bränsle/pris till gasStationController som i sin tur                                 -- 6.)
    // skickar datan tillbaka till frontend



// klasser som behandlar inkommen data                                                                                  -- 7.)



}