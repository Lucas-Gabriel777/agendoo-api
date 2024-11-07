package com.technosdev.services;

import com.technosdev.entities.ServiceOrderEntity;
import com.technosdev.repositories.ServiceOrderRepository;
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
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    public List<ServiceOrderEntity> findAll() {
        return serviceOrderRepository.findAll();
    }

    public ServiceOrderEntity findById(Long id) {
        Optional<ServiceOrderEntity> serviceOrder = serviceOrderRepository.findById(id);
        return serviceOrder.orElseThrow(() -> new ResourceNotFoundException("Usuário não foi encontrado"));
    }

    public ServiceOrderEntity insert(ServiceOrderEntity serviceOrderEntity) {
        return serviceOrderRepository.save(serviceOrderEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            serviceOrderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ServiceOrderEntity update(Long id, ServiceOrderEntity serviceOrderEntity) {
        try {
            ServiceOrderEntity entity = serviceOrderRepository.getReferenceById(id);
            updateData(entity, serviceOrderEntity);
            return serviceOrderRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

    private void updateData(ServiceOrderEntity entity, ServiceOrderEntity serviceOrderEntity) {
        entity.setScheduling(serviceOrderEntity.getScheduling());
        entity.setService(serviceOrderEntity.getService());
        entity.setEmployee(serviceOrderEntity.getEmployee());
    }

}
