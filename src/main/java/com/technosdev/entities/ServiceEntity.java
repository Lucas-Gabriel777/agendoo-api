package com.technosdev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "service")
public class ServiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_service")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_company")
    private CompanyEntity companyEntity;

    @NotBlank(message = "O campo nome é obrigatório")
    @Column(nullable = false, length = 100 , name = "nm_service")
    private String name;

    @Column(nullable = false, length = 200 , name = "ds_service")
    private String description;

    @NotNull(message = "O campo preço é obrigatório")
    @Column(nullable = false , name = "prc_service")
    private Double price;

    @NotNull(message = "O campo tempo médio do serviço é obrigatório")
    @Column(updatable = false , name = "average_time")
    private int averageTime;

    @Column(nullable = false)
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public ServiceEntity() {
    }

    public ServiceEntity(Long codService, Long codCompany, CompanyEntity companyEntity, String nmService, String dsService, Double prcService, int averageTi, boolean active, Date createdAt) {
        this.id = codService;
        this.companyEntity = companyEntity;
        this.name = nmService;
        this.description = dsService;
        this.price = prcService;
        this.averageTime = averageTi;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getCodService() {
        return id;
    }

    public void setCodService(Long codService) {
        this.id = codService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyEntity getCompany() {
        return companyEntity;
    }

    public void setCompany(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceEntity serviceEntity = (ServiceEntity) o;
        return Objects.equals(id, serviceEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
