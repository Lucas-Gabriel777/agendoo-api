package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "scheduling")
public class SchedulingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_scheduling")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_client")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "cod_scheduling_status")
    private SchedulingStatusEntity schedulingStatusEntity;

    @ManyToOne
    @JoinColumn(name = "cod_company")
    private CompanyEntity companyEntity;

    @Column(updatable = false)
    private Date date;

    public SchedulingEntity() {
    }

    public SchedulingEntity(UserEntity userEntity, SchedulingStatusEntity schedulingStatusEntity, CompanyEntity companyEntity, Date date) {
        this.userEntity = userEntity;
        this.schedulingStatusEntity = schedulingStatusEntity;
        this.companyEntity = companyEntity;
        this.date = date;
    }

    public SchedulingEntity(Long id, SchedulingStatusEntity schedulingStatusEntity, UserEntity userEntity, CompanyEntity companyEntity, Date date) {
        this.id = id;
        this.schedulingStatusEntity = schedulingStatusEntity;
        this.userEntity = userEntity;
        this.companyEntity = companyEntity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getClient() {
        return userEntity;
    }

    public void setClient(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public SchedulingStatusEntity getSchedulingStatus() {
        return schedulingStatusEntity;
    }

    public void setSchedulingStatus(SchedulingStatusEntity schedulingStatusEntity) {
        this.schedulingStatusEntity = schedulingStatusEntity;
    }

    public CompanyEntity getCompany() {
        return companyEntity;
    }

    public void setCompany(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingEntity that = (SchedulingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
