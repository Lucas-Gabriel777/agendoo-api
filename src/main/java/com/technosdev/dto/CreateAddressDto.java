package com.technosdev.dto;

public class CreateAddressDto {
    private Long codCity;
    private String publicPlace;
    private String locality;
    private String cep;
    private String complement;
    private double latitude;
    private double longitude;

    public CreateAddressDto(Long codCity, String publicPlace, String locality, String cep, String complement, double latitude, double longitude) {
        this.codCity = codCity;
        this.publicPlace = publicPlace;
        this.locality = locality;
        this.cep = cep;
        this.complement = complement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getCodCity() {
        return codCity;
    }

    public void setCodCity(Long codCity) {
        this.codCity = codCity;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
