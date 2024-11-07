package com.technosdev.services;

import com.technosdev.dto.CreateServiceDto;
import com.technosdev.entities.CompanyEntity;
import com.technosdev.entities.CurrentUserEntity;
import com.technosdev.entities.ServiceEntity;
import com.technosdev.repositories.ServiceRepository;
import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CompanyService companyService;

    public List<ServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    public List<ServiceEntity> findAllServiceByCompanyId() {
        CurrentUserEntity currentUser = (CurrentUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        return serviceRepository.findAllByCompanyId(currentUser.getCompanyEntity().getId());
    }

    public ServiceEntity findById(Long id) {
        Optional<ServiceEntity> client = serviceRepository.findById(id);
        return client.orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
    }

    public ServiceEntity insert(CreateServiceDto createServiceDto) {
        CompanyEntity companyEntity = companyService.findById(createServiceDto.getCodCompany());
        ServiceEntity serviceEntity = new ServiceEntity();

        serviceEntity.setCompany(companyEntity);
        serviceEntity.setName(createServiceDto.getName());
        serviceEntity.setDescription(createServiceDto.getDescription());
        serviceEntity.setPrice(createServiceDto.getPrice());
        serviceEntity.setAverageTime(createServiceDto.getAverageTime());
        serviceEntity.setActive(true);

        return serviceRepository.save(serviceEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            serviceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ServiceEntity update(Long id, ServiceEntity serviceEntity) {
        try {
            ServiceEntity entity = serviceRepository.getReferenceById(id);
            updateData(entity, serviceEntity);
            return serviceRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

    private void updateData(ServiceEntity entity, ServiceEntity client) {
        entity.setCompany(client.getCompany());
        entity.setName(client.getName());
        entity.setDescription(client.getDescription());
        entity.setPrice(client.getPrice());
        entity.setAverageTime(client.getAverageTime());
    }

}
