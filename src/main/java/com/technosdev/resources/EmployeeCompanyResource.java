package com.technosdev.resources;

import com.technosdev.dto.EmployeeCompanyDto;
import com.technosdev.entities.EmployeeCompany;
import com.technosdev.services.EmployeeCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/employeeCompany")
public class EmployeeCompanyResource {
    @Autowired
    private EmployeeCompanyService employeeCompanyService;

    @GetMapping
    public ResponseEntity<List<EmployeeCompany>> findAll() {
        List<EmployeeCompany> employeeCompanyList = employeeCompanyService.findAll();
        return ResponseEntity.ok().body(employeeCompanyList);
    }

    @PostMapping
    public ResponseEntity<EmployeeCompany> insert(@Valid @RequestBody EmployeeCompanyDto employeeCompanyDto){
        EmployeeCompany employeeCompany = employeeCompanyService.insert(employeeCompanyDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(employeeCompany.getId()).toUri();

        return ResponseEntity.created(uri).body(employeeCompany);
    }

    @GetMapping(value = "/company/{codCompany}")
    public ResponseEntity<List<EmployeeCompany>> findById(@Valid @PathVariable Long codCompany) {
        List<EmployeeCompany> employeeCompanyList = employeeCompanyService.findByCodCompany(codCompany);
        return ResponseEntity.ok().body(employeeCompanyList);
    }
}
