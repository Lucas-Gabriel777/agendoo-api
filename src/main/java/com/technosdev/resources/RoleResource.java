package com.technosdev.resources;

import com.technosdev.entities.RoleEntity;
import com.technosdev.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleResource {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleEntity>> findAll() {
        List<RoleEntity> schedulings = roleService.findAll();
        return ResponseEntity.ok().body(schedulings);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleEntity> findById(@Valid @PathVariable Long id) {
        RoleEntity roleEntity = roleService.findById(id);
        return ResponseEntity.ok().body(roleEntity);
    }

    @PostMapping
    public ResponseEntity<RoleEntity> insert(@Valid @RequestBody RoleEntity roleEntity) {
        roleEntity = roleService.insert(roleEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(roleEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(roleEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoleEntity> update(@Valid @PathVariable Long id , @Valid @RequestBody RoleEntity roleEntity) {
        roleService.update(id , roleEntity);
        return ResponseEntity.ok().body(roleEntity);
    }

}
