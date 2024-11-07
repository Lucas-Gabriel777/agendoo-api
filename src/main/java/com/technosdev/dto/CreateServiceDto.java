package com.technosdev.dto;

public class CreateServiceDto {
    private Long codCompany;
    private String name;
    private String description;
    private double price;
    private int averageTime;

    public CreateServiceDto() {
    }

    public CreateServiceDto(Long codCompany, String name, double price, String description, int averageTime) {
        this.codCompany = codCompany;
        this.name = name;
        this.price = price;
        this.description = description;
        this.averageTime = averageTime;
    }

    public Long getCodCompany() {
        return codCompany;
    }

    public void setCodCompany(Long codCompany) {
        this.codCompany = codCompany;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }
}
