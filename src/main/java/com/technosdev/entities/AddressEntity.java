package com.technosdev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_address")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_muni", nullable = false)
    private CityEntity cityEntity;

    @NotBlank(message = "O campo logradouro é obrigatório")
    @Column(nullable = false, length = 50 , name = "nm_public_place")
    private String publicPlace;

    @Column(nullable = false, length = 50, name = "nm_locality")
    @NotBlank(message = "O campo bairro é obrigatório")
    private String locality;

    @NotBlank(message = "O campo cep é obrigatório")
    @Column(nullable = false, length = 8 , name = "nm_cep")
    private String cep;

    @Column(nullable = false, length = 100 , name = "ds_complement")
    private String complement;

    @Column(nullable = false, name = "latitude")
    private double latitude;

    @Column(nullable = false, name = "longitude")
    private double longitude;

    public AddressEntity() {
    }

    public AddressEntity(CityEntity cityEntity, String publicPlace, String locality, String cep, String complement, double latitude, double longitude) {
        this.cityEntity = cityEntity;
        this.publicPlace = publicPlace;
        this.locality = locality;
        this.cep = cep;
        this.complement = complement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressEntity(Long codAddress, CityEntity cityEntity, String nmPublicPlace, String nmLocality, String nmCep, String dsComplement) {
        this.id = codAddress;
        this.cityEntity = cityEntity;
        this.publicPlace = nmPublicPlace;
        this.locality = nmLocality;
        this.cep = nmCep;
        this.complement = dsComplement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CityEntity getCity() {
        return cityEntity;
    }

    public void setCity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getFullAddress() {
        return publicPlace + ", " + locality + ", " + cityEntity.getName() + " - " + cityEntity.getUf().getUf() + ", " + cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity addressEntity = (AddressEntity) o;
        return Objects.equals(id, addressEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
