package com.examensbe.backend.models.gasStation;

public class GasStation {
    private String gasStation;
    private String fuel;
    private String price;
    private String address;
    private int streetNr;
    private int postalCode;
    private String postalTown;
    private String sublocality;

    // Constructors
    public GasStation() {}

    public GasStation(String gasStation, String fuel, String price, String address, int streetNr, int postalCode, String postalTown, String sublocality) {
        this.gasStation = gasStation;
        this.fuel = fuel;
        this.price = price;
        this.address = address;
        this.streetNr = streetNr;
        this.postalCode = postalCode;
        this.postalTown = postalTown;
        this.sublocality = sublocality;
    }

    // Getters and setters
    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(int streetNr) {
        this.streetNr = streetNr;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalTown() {
        return postalTown;
    }

    public void setPostalTown(String postalTown) {
        this.postalTown = postalTown;
    }

    public String getSublocality() {
        return sublocality;
    }

    public void setSublocality(String sublocality) {
        this.sublocality = sublocality;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "GasStation{" +
                "\ngasStation='" + gasStation + '\'' +
                ", fuel='" + fuel + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", streetNr=" + streetNr +
                ", postalCode=" + postalCode +
                ", postalTown='" + postalTown + '\'' +
                ", sublocality='" + sublocality + '\'' +
                '}';
    }
}
