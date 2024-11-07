package com.technosdev.services;

import com.technosdev.entities.CityEntity;
import com.technosdev.repositories.CityRepository;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<CityEntity> findAll() {
        return cityRepository.findAll();
    }

    public CityEntity findById(Long id) {
        Optional<CityEntity> cityEntity = cityRepository.findById(id);
        return cityEntity.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada"));
    }

}
