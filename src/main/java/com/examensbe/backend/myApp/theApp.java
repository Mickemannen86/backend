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
    private final String YOUR_API_KEY;
    private final ObjectMapper objectMapper;
    public theApp(@Value("${GeoCodeKey}") String apiKey, ObjectMapper objectMapper) {
        this.YOUR_API_KEY= apiKey;
        this.objectMapper=objectMapper;
    }
    // Hämta lat long object för check om vi har med oss till theApp (från frontend)
    public void fetchLatLong(double latitude, double longitude) {
        System.out.println("\n1.) myApp tar in koordinaterna: " + latitude + " " + longitude);
    }
    // Method för att skapa URL för geocoding reverse
    public String constructGeocodingURL(double latitude, double longitude) {
        String baseURL = "https://maps.googleapis.com/maps/api/geocode/json";
        String apiKey = YOUR_API_KEY; // Replace with your actual API key
        String url = baseURL + "?latlng=" + latitude + "," + longitude + "&key=" + apiKey; // skydda min key i application.properties
        return url;
    }
    // Method som läser av lat/long för att returnera postal_town

    //* Ny *
    public String reverseGeoCoding(String geoCodingURL) {
        try {
            GeoCodeReverse response =
                    objectMapper.readValue(new URL(geoCodingURL), GeoCodeReverse.class);

            if (response.getResults() == null || response.getResults().isEmpty()) {
                System.out.println("No geocoding results returned from Google");
                return null;
            }

            for (AddressComponent component :
                    response.getResults().get(0).getAddressComponents()) {

                if (component.getTypes().contains("postal_town")
                        || component.getTypes().contains("locality")) {

                    return component.getLongName();
                }
            }

            System.out.println("No suitable address component found");
            return null;

        } catch (IOException e) {
            System.out.println("Reverse geocoding failed: " + e.getMessage());
            return null;
        }
    }
    //* Ny Slutar *

    /* gamla
    public String reverseGeoCoding(String GeoCodingURL) throws IOException {
        try {
            // objectMapper.ReadValue för att köra anropet
            GeoCodeReverse geoCodingResponse = objectMapper.readValue(new URL(GeoCodingURL), GeoCodeReverse.class);
            // Itererar igenom alla address componenterna
            for (AddressComponent addressComponent : geoCodingResponse.getResults().get(0).getAddressComponents()) {
                // Ta ut första postal_town
                if (addressComponent.getTypes().contains("postal_town")) {
                    // Returnera postal_town
                    return addressComponent.getLongName();
                }
            }
            System.out.println("Postal town not found.");
            return null; // Returnerar null om postal town inte hittas
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching geocoding data: " + e.getMessage());
            return null; // Returnerar null om ett fel uppstår
        }
    }*/

    // Method frågar fakeStations.json för lista med gas stations

    //* Ny *
    public List<GasStation> queryGasStations(String postalTown) throws IOException {
        if (postalTown == null) {
            return List.of();
        }

        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("fakeStations.json");

        List<GasStation> gasStations =
                objectMapper.readValue(inputStream, new TypeReference<>() {});

        return gasStations.stream()
                .filter(station ->
                        station.getPostalTown() != null &&
                                station.getPostalTown().equalsIgnoreCase(postalTown))
                .toList();
    }
    //* Ny Slutar *
    /* gamla
    public List<GasStation> queryGasStations(String postal_town) throws IOException {
        // Ladda in fakeStations.json
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("fakeStations.json");
        List<GasStation> gasStations = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        // Filtrerar gas stations med matchad postal_town
        List<GasStation> filteredGasStations = gasStations.stream()
                .filter(station -> station.getPostalTown() != null && station.getPostalTown().equalsIgnoreCase(postal_town))
                .collect(Collectors.toList());
        return filteredGasStations;
    }
    */


    // Method som hämtar gas station med lägst price

    //* Ny Slutar *
    public GasStation selectGasStationWithLowestPrice(List<GasStation> gasStations) {
        return gasStations.stream()
                .min(Comparator.comparingDouble(station -> parsePrice(station.getPrice())))
                .orElse(null);
    }

    //* Ny Slutar *

     /* gamla
    public GasStation selectGasStationWithLowestPrice(List<GasStation> gasStations) throws IOException {
        // Hitta gas station med lägst price
        Optional<GasStation> cheapestGasStation = gasStations.stream()
                .min(Comparator.comparingDouble(station -> parsePrice(station.getPrice())));

        // If a cheapest gas station is found, return its details
        if (cheapestGasStation.isPresent()) {
            GasStation cheapestStation = cheapestGasStation.get();
            return cheapestStation;
        } else {
            // If no gas station is found, return a message indicating so
            System.out.println("No gas station found.");
            return null;
        }
    }

    */
    // Hjälp method som parse price string to double
    private double parsePrice(String price) {
        // Remove non-numeric characters, replace comma with dot, and parse to double
        return Double.parseDouble(price.replaceAll("[^\\d.,]", "").replace(",", "."));
    }
    // Method to process latitude and longitude to return gasStation with the lowest price
    public GasStation processLatitudeLongitude(double latitude, double longitude) throws IOException {
        // 1. Anropar fetchLatLong för att hantera latitud och longitud från frontend.
        fetchLatLong(latitude, longitude); // Få notis om att lat lng nått theApp från frontend
        // 2. Skapar en URL för geocoding med constructGeocodingURL och sätter in latitud och longitud i den.
        String geocodingURL = constructGeocodingURL(latitude, longitude); // Skapa anrop URL sätt in värden lat lng
        // 3. Hämtar ut postal_town från min ReverseGeocode response
        String postalTownResult = reverseGeoCoding(geocodingURL);
        // 4. Frågar fakeStations.json efter bensinstationer baserat på den postal_town värdet returnerat av ReverseGeoCoding()
        List<GasStation> gasStations = queryGasStations(postalTownResult);
        // 5. Väljer den bensinstationen med lägsta priset från resultatet med selectGasStationWithLowestPrice.
        GasStation selectedGasStation = selectGasStationWithLowestPrice(gasStations);
        // 6. Returnerar gas station data till controller
        System.out.println("\n11.) Slutresultat Check: " + selectedGasStation);
        return selectedGasStation;
    }

}