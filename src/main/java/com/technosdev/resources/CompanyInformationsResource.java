package com.technosdev.resources;

import com.technosdev.dto.CreateCompanyInformationsDto;
import com.technosdev.entities.CompanyInformationsEntity;
import com.technosdev.services.CompanyInformationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/companyInformations")
public class CompanyInformationsResource {
    @Autowired
    private CompanyInformationsService companyInformationsService;

    @GetMapping
    public ResponseEntity<List<CompanyInformationsEntity>> findAll() {
        List<CompanyInformationsEntity> informationsServiceAll = companyInformationsService.findAll();
        return ResponseEntity.ok().body(informationsServiceAll);
    }

    @PostMapping
    public ResponseEntity<CompanyInformationsEntity> insert(@Valid @RequestBody CreateCompanyInformationsDto createCompanyInformationsDto){
        CompanyInformationsEntity companyInformationsEntity = companyInformationsService.insert(createCompanyInformationsDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(companyInformationsEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(companyInformationsEntity);
    }
}
