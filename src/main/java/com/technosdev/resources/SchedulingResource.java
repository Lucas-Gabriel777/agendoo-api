package com.technosdev.resources;

import com.technosdev.entities.SchedulingEntity;
import com.technosdev.services.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/scheduling")
public class SchedulingResource {
    @Autowired
    private SchedulingService schedulingService;

    @GetMapping
    public ResponseEntity<List<SchedulingEntity>> findAll() {
        List<SchedulingEntity> schedulingEntities = schedulingService.findAll();
        return ResponseEntity.ok().body(schedulingEntities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SchedulingEntity> findById(@Valid @PathVariable Long id) {
        SchedulingEntity schedulingEntity = schedulingService.findById(id);
        return ResponseEntity.ok().body(schedulingEntity);
    }

    @PostMapping
    public ResponseEntity<SchedulingEntity> insert(@Valid @RequestBody SchedulingEntity schedulingEntity) {
        schedulingEntity = schedulingService.insert(schedulingEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(schedulingEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(schedulingEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        schedulingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SchedulingEntity> update(@Valid @PathVariable Long id , @Valid @RequestBody SchedulingEntity schedulingEntity) {
        schedulingService.update(id , schedulingEntity);
        return ResponseEntity.ok().body(schedulingEntity);
    }

}
