package com.technosdev.resources;

import com.technosdev.entities.EmployeeEntity;
import com.technosdev.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeResource {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> findAll() {
        List<EmployeeEntity> employeeEntities = employeeService.findAll();
        return ResponseEntity.ok().body(employeeEntities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeEntity> findById(@Valid @PathVariable Long id) {
        EmployeeEntity employeeEntity = employeeService.findById(id);
        return ResponseEntity.ok().body(employeeEntity);
    }

    @PostMapping
    public ResponseEntity<EmployeeEntity> insert(@Valid @RequestBody EmployeeEntity employeeEntity){

        employeeEntity.setActive(true);
        employeeEntity = employeeService.insert(employeeEntity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(employeeEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(employeeEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeEntity> update(@Valid @PathVariable Long id, @Valid @RequestBody EmployeeEntity employeeEntity) {
        employeeEntity = employeeService.update(id, employeeEntity);
        return ResponseEntity.ok().body(employeeEntity);
    }

}
