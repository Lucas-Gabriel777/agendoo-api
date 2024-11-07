package com.technosdev.resources;

import com.technosdev.dto.CreateServiceDto;
import com.technosdev.entities.ServiceEntity;
import com.technosdev.services.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class ServiceResource {
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> findAll() {
        List<ServiceEntity> serviceEntities = serviceService.findAllServiceByCompanyId();
        return ResponseEntity.ok().body(serviceEntities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceEntity> findById(@Valid @PathVariable Long id) {
        ServiceEntity serviceEntity = serviceService.findById(id);
        return ResponseEntity.ok().body(serviceEntity);
    }

    @PostMapping
    public ResponseEntity<ServiceEntity> insert(@Valid @RequestBody CreateServiceDto createServiceDto) {
        ServiceEntity serviceEntity = serviceService.insert(createServiceDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(serviceEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(serviceEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ServiceEntity> update(@Valid @PathVariable Long id, @Valid @RequestBody ServiceEntity serviceEntity) {
        serviceEntity = serviceService.update(id, serviceEntity);
        return ResponseEntity.ok().body(serviceEntity);
    }

}
