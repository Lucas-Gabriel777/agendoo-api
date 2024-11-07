package com.technosdev.resources;

import com.technosdev.dto.EmployeeServiceDto;
import com.technosdev.entities.EmployeeServiceEntity;
import com.technosdev.services.EmployeeServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/employeeService")
public class EmployeeServiceResource {
    @Autowired
    private EmployeeServiceService employeeServiceService;

    @GetMapping
    public ResponseEntity<List<EmployeeServiceEntity>> findAll() {
        List<EmployeeServiceEntity> employeeServiceEntities = employeeServiceService.findAll();
        return ResponseEntity.ok().body(employeeServiceEntities);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<EmployeeServiceEntity> getById(@PathVariable Long id){
        EmployeeServiceEntity employeeServicesEntity = employeeServiceService.findById(id);
        return ResponseEntity.ok().body(employeeServicesEntity);
    }

    @PostMapping
    public ResponseEntity<EmployeeServiceEntity> insert(@Valid @RequestBody EmployeeServiceDto employeeServiceDto){
        EmployeeServiceEntity employeeServiceEntity = employeeServiceService.insert(employeeServiceDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(employeeServiceEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(employeeServiceEntity);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        employeeServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeServiceEntity> update(@Valid @PathVariable Long id, @Valid @RequestBody EmployeeServiceEntity employeeServiceEntity) {
        employeeServiceEntity = employeeServiceService.update(id, employeeServiceEntity);
        return ResponseEntity.ok().body(employeeServiceEntity);
    }

    @GetMapping(value = "/employee/{codEmployee}")
    public ResponseEntity<List<EmployeeServiceEntity>> listServicesByCodEmployee(@PathVariable Long codEmployee){
        List<EmployeeServiceEntity> employeeServiceEntityList = employeeServiceService.listServicesByCodEmployee(codEmployee);
        return ResponseEntity.ok().body(employeeServiceEntityList);
    }
}
