package com.technosdev.services;

import com.technosdev.dto.EmployeeCompanyDto;
import com.technosdev.entities.CompanyEntity;
import com.technosdev.entities.EmployeeEntity;
import com.technosdev.entities.EmployeeCompany;
import com.technosdev.repositories.EmployeeCompanyRepository;
import com.technosdev.services.exceptions.UnprocessableEntityException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeCompanyService {
    @Autowired
    private EmployeeCompanyRepository employeeCompanyRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    public List<EmployeeCompany> findAll() {
        return employeeCompanyRepository.findAll();
    }

    public EmployeeCompany insert(@Valid EmployeeCompanyDto employeeCompanyDto) {
        EmployeeCompany employeeCompany = new EmployeeCompany();

        EmployeeEntity employeeEntity = employeeService.findById(employeeCompanyDto.getCodEmployee());

        if (employeeEntity == null) {
            throw new UnprocessableEntityException("Trabalhador não existe");
        }

        CompanyEntity companyEntity = companyService.findById(employeeCompanyDto.getCodCompany());

        if (companyEntity == null) {
            throw new UnprocessableEntityException("Empresa não existe");
        }

        employeeCompany.setEmployee(employeeEntity);
        employeeCompany.setCompany(companyEntity);

        return employeeCompanyRepository.save(employeeCompany);
    }

    public List<EmployeeCompany> findByCodCompany(@Valid Long codCompany) {
        return employeeCompanyRepository.findByCodCompany(codCompany);
    }
}
