package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "service_order")
public class ServiceOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_service_order")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_scheduling")
    private SchedulingEntity schedulingEntity;

    @ManyToOne
    @JoinColumn(name = "cod_service")
    private ServiceEntity serviceEntity;

    @ManyToOne
    @JoinColumn(name = "cod_employee")
    private EmployeeEntity employeeEntity;

    @Column(updatable = false)
    private Date finishedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public ServiceOrderEntity() {
    }

    public ServiceOrderEntity(Long id, SchedulingEntity schedulingEntity, ServiceEntity serviceEntity, EmployeeEntity employeeEntity, Date finishedAt, Date createdAt) {
        this.id = id;
        this.schedulingEntity = schedulingEntity;
        this.serviceEntity = serviceEntity;
        this.employeeEntity = employeeEntity;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchedulingEntity getScheduling() {
        return schedulingEntity;
    }

    public void setScheduling(SchedulingEntity schedulingEntity) {
        this.schedulingEntity = schedulingEntity;
    }

    public ServiceEntity getService() {
        return serviceEntity;
    }

    public void setService(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public EmployeeEntity getEmployee() {
        return employeeEntity;
    }

    public void setEmployee(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
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
        ServiceOrderEntity that = (ServiceOrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
