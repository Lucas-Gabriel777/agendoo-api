package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class EmployeeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_empl")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_user")
    private UserEntity userEntity;

    @Column(nullable = false, length = 20 , name = "contact_user")
    private String contactUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "hired_in")
    private Date hiredIn;

    @Column(nullable = false)
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        hiredIn = new Date();
    }

    public EmployeeEntity() {
    }

    public EmployeeEntity(Long id, UserEntity userEntity, String contactUser, Date hiredIn, boolean active, Date createdAt) {
        this.id = id;
        this.userEntity = userEntity;
        this.contactUser = contactUser;
        this.hiredIn = hiredIn;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public Date getHiredIn() {
        return hiredIn;
    }

    public void setHiredIn(Date hiredIn) {
        this.hiredIn = hiredIn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity employeeEntity = (EmployeeEntity) o;
        return Objects.equals(id, employeeEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

