package com.technosdev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_company")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_address")
    private AddressEntity addressEntity;

    @ManyToOne
    @JoinColumn(name = "cod_user")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "cod_flow_informations")
    private FlowInformationsEntity flowInformationsEntity;

    @ManyToOne
    @JoinColumn(name = "cod_company_informations")
    private CompanyInformationsEntity companyInformationsEntity;

    @Column(nullable = false , name = "active")
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    public CompanyEntity() {
    }

    public CompanyEntity(AddressEntity addressEntity, UserEntity userEntity, FlowInformationsEntity flowInformationsEntity, CompanyInformationsEntity companyInformationsEntity, boolean active) {
        this.addressEntity = addressEntity;
        this.userEntity = userEntity;
        this.flowInformationsEntity = flowInformationsEntity;
        this.companyInformationsEntity = companyInformationsEntity;
        this.active = active;
    }

    public CompanyEntity(Long id, AddressEntity addressEntity, UserEntity userEntity, FlowInformationsEntity flowInformationsEntity, CompanyInformationsEntity companyInformationsEntity, boolean active, Date createdAt) {
        this.id = id;
        this.addressEntity = addressEntity;
        this.userEntity = userEntity;
        this.flowInformationsEntity = flowInformationsEntity;
        this.companyInformationsEntity = companyInformationsEntity;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public FlowInformationsEntity getFlowInformationsEntity() {
        return flowInformationsEntity;
    }

    public void setFlowInformationsEntity(FlowInformationsEntity flowInformationsEntity) {
        this.flowInformationsEntity = flowInformationsEntity;
    }

    public CompanyInformationsEntity getCompanyInformationsEntity() {
        return companyInformationsEntity;
    }

    public void setCompanyInformationsEntity(CompanyInformationsEntity companyInformationsEntity) {
        this.companyInformationsEntity = companyInformationsEntity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity companyEntity = (CompanyEntity) o;
        return Objects.equals(id, companyEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", addressEntity=" + addressEntity +
                ", userEntity=" + userEntity +
                ", flowInformationsEntity=" + flowInformationsEntity +
                ", companyInformationsEntity=" + companyInformationsEntity +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
