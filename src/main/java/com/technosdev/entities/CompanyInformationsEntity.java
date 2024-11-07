package com.technosdev.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "company_informations")
public class CompanyInformationsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_company_informations")
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Column(nullable = false, length = 100 , name = "nm_company")
    private String name;

    @NotBlank(message = "O campo cnpj é obrigatório")
    @Column(nullable = false, length = 30 , name = "nmr_cnpj")
    private String cnpj;

    @NotBlank(message = "O campo e-mail é obrigatório")
    @Column(nullable = false, length = 50 , name = "nm_email")
    private String email;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Size(min = 12 , max = 12 , message = "Número de telefone mal formatado. Verifique o formato e tente novamente.")
    @Column(nullable = false, length = 20 , name = "nmr_phone")
    private String phone;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "start_time")
    private Time startTime;

    @Column(nullable = false, name = "end_time")
    private Time endTime;

    @Column(nullable = false, name = "average_time_work")
    private int averageTimeWork;

    public CompanyInformationsEntity() {
    }

    public CompanyInformationsEntity(Long id, String name, String cnpj, String email, String phone, String password, Time startTime, Time endTime, int averageTimeWork) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.startTime = startTime;
        this.endTime = endTime;
        this.averageTimeWork = averageTimeWork;
    }

    public CompanyInformationsEntity(String name, String cnpj, String email, String phone, String password, Time startTime, Time endTime, int averageTimeWork) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.startTime = startTime;
        this.endTime = endTime;
        this.averageTimeWork = averageTimeWork;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getAverageTimeWork() {
        return averageTimeWork;
    }

    public void setAverageTimeWork(int averageTimeWork) {
        this.averageTimeWork = averageTimeWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyInformationsEntity that = (CompanyInformationsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
