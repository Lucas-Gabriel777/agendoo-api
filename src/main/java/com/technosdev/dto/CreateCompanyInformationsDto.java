package com.technosdev.dto;

public class CreateCompanyInformationsDto {
    private String name;
    private String cnpj;
    private String email;
    private String phone;
    private String password;
    private String startTime;
    private String endTime;
    private int averageTimeWork;

    public CreateCompanyInformationsDto(String name, String cnpj, String email, String phone, String password, String startTime, String endTime, int averageTimeWork) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.startTime = startTime;
        this.endTime = endTime;
        this.averageTimeWork = averageTimeWork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getAverageTimeWork() {
        return averageTimeWork;
    }

    public void setAverageTimeWork(int averageTimeWork) {
        this.averageTimeWork = averageTimeWork;
    }
}
