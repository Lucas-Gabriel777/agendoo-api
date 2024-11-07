package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "city")
public class CityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_city")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_state")
    private StateEntity stateEntity;

    @Column(nullable = false, length = 50 , name = "nm_city")
    private String name;

    public CityEntity() {
    }

    public CityEntity(Long codCity, String nmCity, StateEntity stateEntity) {
        this.id = codCity;
        this.name = nmCity;
        this.stateEntity = stateEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateEntity getUf() {
        return stateEntity;
    }

    public void setUf(StateEntity uf) {
        this.stateEntity = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity cityEntity = (CityEntity) o;
        return Objects.equals(id, cityEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
