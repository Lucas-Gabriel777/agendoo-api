package com.technosdev.dto;

public class EmployeeServiceDto {
    private Long codService;
    private Long codEmployee;

    public EmployeeServiceDto(Long codEmployee, Long codService) {
        this.codEmployee = codEmployee;
        this.codService = codService;
    }

    public Long getCodService() {
        return codService;
    }

    public void setCodService(Long codService) {
        this.codService = codService;
    }

    public Long getCodEmployee() {
        return codEmployee;
    }

    public void setCodEmployee(Long codEmployee) {
        this.codEmployee = codEmployee;
    }
}
