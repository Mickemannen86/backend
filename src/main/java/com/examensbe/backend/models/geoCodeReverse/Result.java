package com.examensbe.backend.models.geoCodeReverse;

import java.util.List;


import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address_components",
        "formatted_address",
        "geometry",
        "place_id",
        "plus_code",
        "types"
})
@Generated("jsonschema2pojo")
public class Result {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;
    @JsonProperty("formatted_address")
    private String formattedAddress;
    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("plus_code")
    private PlusCode__1 plusCode;
    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("address_components")
    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    @JsonProperty("address_components")
    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    @JsonProperty("formatted_address")
    public String getFormattedAddress() {
        return formattedAddress;
    }

    @JsonProperty("formatted_address")
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    @JsonProperty("geometry")
    public Geometry getGeometry() {
        return geometry;
    }

    @JsonProperty("geometry")
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @JsonProperty("place_id")
    public String getPlaceId() {
        return placeId;
    }

    @JsonProperty("place_id")
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @JsonProperty("plus_code")
    public PlusCode__1 getPlusCode() {
        return plusCode;
    }

    @JsonProperty("plus_code")
    public void setPlusCode(PlusCode__1 plusCode) {
        this.plusCode = plusCode;
    }

    @JsonProperty("types")
    public List<String> getTypes() {
        return types;
    }

    @JsonProperty("types")
    public void setTypes(List<String> types) {
        this.types = types;
    }

}
