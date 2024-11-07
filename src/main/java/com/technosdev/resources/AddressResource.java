package com.technosdev.resources;

import com.technosdev.dto.CreateAddressDto;
import com.technosdev.entities.AddressEntity;
import com.technosdev.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/address")
public class AddressResource {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressEntity>> findAll() {
        List<AddressEntity> addressEntities = addressService.findAll();
        return ResponseEntity.ok().body(addressEntities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressEntity> findById(@Valid @PathVariable Long id) {
        AddressEntity addressEntity = addressService.findById(id);
        return ResponseEntity.ok().body(addressEntity);
    }

    @PostMapping
    public ResponseEntity<AddressEntity> insert(@Valid @RequestBody CreateAddressDto createAddressDto){

        String cep = createAddressDto.getCep();

        Pattern pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");
        Matcher matcher = pattern.matcher(cep);
        boolean cepIsValid = matcher.find();

        if (!cepIsValid){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }

        AddressEntity addressEntity = addressService.insert(createAddressDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(addressEntity.getId()).toUri();

        return ResponseEntity.created(uri).body(addressEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressEntity> update(@Valid @PathVariable Long id, @RequestBody AddressEntity addressEntity) {
        addressEntity = addressService.update(id, addressEntity);
        return ResponseEntity.ok().body(addressEntity);
    }

}
