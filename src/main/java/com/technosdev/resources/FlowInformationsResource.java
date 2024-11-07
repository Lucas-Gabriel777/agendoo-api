package com.technosdev.resources;

import com.technosdev.entities.FlowInformationsEntity;
import com.technosdev.services.FlowInformationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/flowInformations")
public class FlowInformationsResource {
    @Autowired
    private FlowInformationsService flowInformationsService;

    @PostMapping
    public ResponseEntity<FlowInformationsEntity> insert(@Valid @RequestBody FlowInformationsEntity flowInformationsEntity) {
        flowInformationsEntity = flowInformationsService.insert(flowInformationsEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(flowInformationsEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(flowInformationsEntity);
    }
}
