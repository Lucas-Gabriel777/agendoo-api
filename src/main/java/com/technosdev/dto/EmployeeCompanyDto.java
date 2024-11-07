package com.technosdev.dto;

public class EmployeeCompanyDto {
    private Long codCompany;
    private Long codEmployee;

    public EmployeeCompanyDto(Long codEmployee, Long codCompany) {
        this.codEmployee = codEmployee;
        this.codCompany = codCompany;
    }

    public Long getCodCompany() {
        return codCompany;
    }

    public void setCodCompany(Long codCompany) {
        this.codCompany = codCompany;
    }

    public Long getCodEmployee() {
        return codEmployee;
    }

    public void setCodEmployee(Long codEmployee) {
        this.codEmployee = codEmployee;
    }
}
