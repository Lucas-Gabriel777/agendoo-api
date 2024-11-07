package com.technosdev.resources;

import com.technosdev.entities.ServiceOrderEntity;
import com.technosdev.services.ServiceOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/serviceOrder")
public class ServiceOrderResource {
    @Autowired
    private ServiceOrderService serviceOrderService;

    @GetMapping
    public ResponseEntity<List<ServiceOrderEntity>> findAll() {
        List<ServiceOrderEntity> serviceOrderEntities = serviceOrderService.findAll();
        return ResponseEntity.ok().body(serviceOrderEntities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceOrderEntity> getById(@PathVariable Long id) {
        ServiceOrderEntity serviceOrderEntity = serviceOrderService.findById(id);
        return ResponseEntity.ok().body(serviceOrderEntity);
    }

    @PostMapping
    public ResponseEntity<ServiceOrderEntity> insert(@Valid @RequestBody ServiceOrderEntity serviceOrderEntity) {
        serviceOrderEntity = serviceOrderService.insert(serviceOrderEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(serviceOrderEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(serviceOrderEntity);
    }

}
