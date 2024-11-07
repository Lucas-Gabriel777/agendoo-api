package com.technosdev.resources;

import com.technosdev.dto.CreateCompanyDto;
import com.technosdev.entities.AddressEntity;
import com.technosdev.entities.CompanyEntity;
import com.technosdev.services.AddressService;
import com.technosdev.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping(value = "/company")
public class CompanyResource {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<CompanyEntity>> findAll() {
        List<CompanyEntity> companies = companyService.findAll();
        return ResponseEntity.ok().body(companies);
    }

    @PostMapping
    public ResponseEntity<CompanyEntity> insert(@Valid @RequestBody CreateCompanyDto createCompanyDto){
        CompanyEntity companyEntity = companyService.insert(createCompanyDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(companyEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(companyEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CompanyEntity> update(@Valid @PathVariable Long id, @Valid @RequestBody CompanyEntity companyEntity) {
        AddressEntity addressEntity = addressService.update(companyEntity.getAddressEntity().getId() , companyEntity.getAddressEntity());
        companyEntity = companyService.update(id, companyEntity, addressEntity);
        return ResponseEntity.ok().body(companyEntity);
    }

}
