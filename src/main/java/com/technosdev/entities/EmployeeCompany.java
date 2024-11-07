package com.technosdev.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "employee_company")
public class EmployeeCompany implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_employee_company")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_employee")
    private EmployeeEntity employeeEntity;

    @ManyToOne
    @JoinColumn(name = "cod_company")
    private CompanyEntity companyEntity;

    public EmployeeCompany() {
    }

    public EmployeeCompany(EmployeeEntity employeeEntity, CompanyEntity companyEntity) {
        this.employeeEntity = employeeEntity;
        this.companyEntity = companyEntity;
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

    public EmployeeEntity getEmployee() {
        return employeeEntity;
    }

    public void setEmployee(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }
}
