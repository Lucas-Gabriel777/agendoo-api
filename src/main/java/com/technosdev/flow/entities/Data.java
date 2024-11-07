package com.technosdev.flow.entities;

public class Data {
    private String clientName;
    private String cpf;
    private String termsAccept;

    private String companyNumber;

    private String time;
    private String day;
    private String employeeId;
    private String selectService;

    public Data() {
    }

    public Data(String clientName, String cpf, String termsAccept, String companyNumber) {
        this.clientName = clientName;
        this.cpf = cpf;
        this.termsAccept = termsAccept;
        this.companyNumber = companyNumber;
    }

    public Data(String clientName, String cpf, String termsAccept, String companyNumber, String time, String employeeId, String day, String selectService) {
        this.clientName = clientName;
        this.cpf = cpf;
        this.termsAccept = termsAccept;
        this.companyNumber = companyNumber;
        this.time = time;
        this.employeeId = employeeId;
        this.day = day;
        this.selectService = selectService;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTermsAccept() {
        return termsAccept;
    }

    public boolean isTermsAccept() {
        return Boolean.parseBoolean(termsAccept);
    }

    public void setTermsAccept(String termsAccept) {
        this.termsAccept = termsAccept;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSelectService() {
        return selectService;
    }

    public void setSelectService(String selectService) {
        this.selectService = selectService;
    }

    @Override
    public String toString() {
        return "Data{" +
                "clientName='" + clientName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", termsAccept='" + termsAccept + '\'' +
                '}';
    }
}
