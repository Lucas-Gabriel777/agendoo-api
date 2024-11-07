package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "state")
public class StateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_state")
    private Long id;

    @Column(nullable = false, length = 50 , name = "nm_state")
    private String name;

    @Column(nullable = false, length = 2 , name = "sg_uf")
    private String uf;

    public StateEntity() {
    }

    public StateEntity(Long codState, String nmState, String uf) {
        this.id = codState;
        this.name = nmState;
        this.uf = uf;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateEntity stateEntity = (StateEntity) o;
        return Objects.equals(id, stateEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
