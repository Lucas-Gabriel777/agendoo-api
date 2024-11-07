package com.technosdev.resources;

import com.technosdev.dto.CreateUserRoleDto;
import com.technosdev.entities.UserRolesEntity;
import com.technosdev.services.UserRolesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/userRoles")
public class UserRolesResource {

    @Autowired
    private UserRolesService userRolesService;

    @GetMapping
    public ResponseEntity<List<UserRolesEntity>> findAll() {
        List<UserRolesEntity> schedulings = userRolesService.findAll();
        return ResponseEntity.ok().body(schedulings);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRolesEntity> findById(@Valid @PathVariable Long id) {
        UserRolesEntity group = userRolesService.findById(id);
        return ResponseEntity.ok().body(group);
    }

    @PostMapping
    public ResponseEntity<UserRolesEntity> insert(@Valid @RequestBody CreateUserRoleDto createUserRoleDto) {
        UserRolesEntity userRoles = userRolesService.insert(createUserRoleDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userRoles.getId()).toUri();

        return ResponseEntity.created(uri).body(userRoles);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        userRolesService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserRolesEntity> update(@Valid @PathVariable Long id , @Valid @RequestBody UserRolesEntity group) {
        userRolesService.update(id , group);
        return ResponseEntity.ok().body(group);
    }

}
