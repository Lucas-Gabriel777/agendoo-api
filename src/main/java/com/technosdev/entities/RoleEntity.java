package com.technosdev.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_role")
    private Long id;

    @Column(name = "nm_role" , updatable = false)
    private String name;

    public RoleEntity() {}

    public RoleEntity(String name){
        this.name = name;
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

}
