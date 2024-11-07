package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "employee_service")
public class EmployeeServiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_employee_service")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_service")
    private ServiceEntity serviceEntity;

    @ManyToOne
    @JoinColumn(name = "cod_employee")
    private EmployeeEntity employeeEntity;

    public EmployeeServiceEntity() {
    }

    public EmployeeServiceEntity(Long codService, ServiceEntity serviceEntity, Long codEmployee, EmployeeEntity employeeEntity) {
        this.serviceEntity = serviceEntity;
        this.employeeEntity = employeeEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceEntity getService() {
        return serviceEntity;
    }

    public void setService(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    public EmployeeEntity getEmployee() {
        return employeeEntity;
    }

    public void setEmployee(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }
}
