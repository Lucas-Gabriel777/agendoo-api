package com.technosdev.resources;

import com.technosdev.dto.CreateUserDto;
import com.technosdev.entities.UserEntity;
import com.technosdev.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER')")
    public ResponseEntity<List<UserEntity>> findAll() {
        List<UserEntity> userEntities = userService.findAll();
        return ResponseEntity.ok().body(userEntities);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER')")
    public ResponseEntity<UserEntity> findById(@Valid @PathVariable Long id) {
        UserEntity userEntity = userService.findById(id);
        return ResponseEntity.ok().body(userEntity);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER')")
    public ResponseEntity<UserEntity> insert(@Valid @RequestBody CreateUserDto createUserDto) {
        UserEntity userEntity = userService.insert(createUserDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
               .buildAndExpand(userEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(userEntity);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER')")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER')")
    public ResponseEntity<UserEntity> update(@Valid @PathVariable Long id, @Valid @RequestBody UserEntity userEntity) {
        userEntity = userService.update(id, userEntity);
        return ResponseEntity.ok().body(userEntity);
    }
}
