package com.technosdev.resources;

import com.technosdev.entities.CityEntity;
import com.technosdev.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/county")
public class CityResource {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityEntity>> findAll() {
        List<CityEntity> counties = cityService.findAll();
        return ResponseEntity.ok().body(counties);
    }

}
