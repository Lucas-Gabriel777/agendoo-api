package com.technosdev.resources;

import com.technosdev.entities.SchedulingStatusEntity;
import com.technosdev.services.SchedulingStatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/schedulingStatus")
public class SchedulingStatusResource {

    @Autowired
    private SchedulingStatusService schedulingStatusService;

    @GetMapping
    public ResponseEntity<List<SchedulingStatusEntity>> findAll() {
        List<SchedulingStatusEntity> schedulingStatusEntities = schedulingStatusService.findAll();
        return ResponseEntity.ok().body(schedulingStatusEntities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SchedulingStatusEntity> getById(@PathVariable Long id){
        SchedulingStatusEntity schedulingStatusEntity = schedulingStatusService.findById(id);
        return ResponseEntity.ok().body(schedulingStatusEntity);
    }

    @PostMapping
    public ResponseEntity<SchedulingStatusEntity> insert(@Valid @RequestBody SchedulingStatusEntity schedulingStatusEntity){
        schedulingStatusEntity = schedulingStatusService.insert(schedulingStatusEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(schedulingStatusEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(schedulingStatusEntity);
    }

}
