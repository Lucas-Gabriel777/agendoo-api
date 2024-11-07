package com.technosdev.services;

import com.technosdev.dto.CreateAddressDto;
import com.technosdev.entities.AddressEntity;
import com.technosdev.entities.CityEntity;
import com.technosdev.repositories.AddressRepository;
import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityService cityService;

    public List<AddressEntity> findAll() {
        return addressRepository.findAll();
    }

    public AddressEntity findById(Long id) {
        Optional<AddressEntity> address = addressRepository.findById(id);
        return address.orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    public AddressEntity insert(CreateAddressDto createAddressDto) {
        CityEntity cityEntity = cityService.findById(createAddressDto.getCodCity());

        AddressEntity addressEntity = new AddressEntity(cityEntity, createAddressDto.getPublicPlace(), createAddressDto.getLocality(), createAddressDto.getCep(), createAddressDto.getComplement(), createAddressDto.getLatitude(), createAddressDto.getLongitude());
        return addressRepository.save(addressEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            addressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public AddressEntity update(Long id, AddressEntity addressEntity) {
        try {
            AddressEntity entity = addressRepository.getReferenceById(id);
            updateData(entity, addressEntity);
            return addressRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
    }

    private void updateData(AddressEntity entity, AddressEntity addressEntity) {
        entity.setCity(addressEntity.getCity());
        entity.setPublicPlace(addressEntity.getPublicPlace());
        entity.setLocality(addressEntity.getLocality());
        entity.setCep(addressEntity.getCep());
        entity.setComplement(addressEntity.getComplement());
    }

}
