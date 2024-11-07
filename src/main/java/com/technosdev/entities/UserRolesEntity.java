package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user_roles")
public class UserRolesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_user_role")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_user")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "cod_role")
    private RoleEntity roleEntity;

    public UserRolesEntity() {  }

    public UserRolesEntity(UserEntity userEntity, RoleEntity roleEntity) {
        this.userEntity = userEntity;
        this.roleEntity = roleEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RoleEntity getRole() {
        return roleEntity;
    }

    public void setRole(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
