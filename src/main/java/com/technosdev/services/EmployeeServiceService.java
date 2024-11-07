package com.technosdev.services;

import com.technosdev.dto.EmployeeServiceDto;
import com.technosdev.entities.EmployeeEntity;
import com.technosdev.entities.EmployeeServiceEntity;
import com.technosdev.entities.ServiceEntity;
import com.technosdev.repositories.EmployeeServiceRepository;
import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import com.technosdev.services.exceptions.UnprocessableEntityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceService {
    @Autowired
    private EmployeeServiceRepository employeeServiceRepository;

    @Autowired
    private com.technosdev.services.EmployeeService employeeService;

    @Autowired
    private ServiceService serviceService;

    public List<EmployeeServiceEntity> findAll() {
        return employeeServiceRepository.findAll();
    }

    public EmployeeServiceEntity findById(Long id) {
        Optional<EmployeeServiceEntity> employee = employeeServiceRepository.findById(id);
        return employee.orElseThrow(() -> new ResourceNotFoundException("Serviço do colaborador não encontrado"));
    }

    public EmployeeServiceEntity insert(EmployeeServiceDto employeeServiceDto) {
        EmployeeServiceEntity employeeServiceEntityModel = new EmployeeServiceEntity();

        EmployeeEntity employeeEntity = employeeService.findById(employeeServiceDto.getCodEmployee());

        if (employeeEntity == null) {
            throw new UnprocessableEntityException("Trabalhador não existe");
        }

        ServiceEntity serviceEntity = serviceService.findById(employeeServiceDto.getCodService());

        if (serviceEntity == null) {
            throw new UnprocessableEntityException("Serviço não existe");
        }

        employeeServiceEntityModel.setEmployee(employeeEntity);
        employeeServiceEntityModel.setService(serviceEntity);

        return employeeServiceRepository.save(employeeServiceEntityModel);
    }

    public void delete(Long id) {
        try {
            findById(id);
            employeeServiceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Serviço do colaborador não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public EmployeeServiceEntity update(Long id, EmployeeServiceEntity employeeServiceEntity) {
        try {
            EmployeeServiceEntity entity = employeeServiceRepository.getReferenceById(id);
            updateData(entity, employeeServiceEntity);
            return employeeServiceRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }

    private void updateData(EmployeeServiceEntity entity, EmployeeServiceEntity employeeServiceEntity) {
        entity.setEmployee(employeeServiceEntity.getEmployee());
        entity.setService(employeeServiceEntity.getService());
    }

    public List<EmployeeServiceEntity> listServicesByCodEmployee(Long codEmployee) {
        return employeeServiceRepository.listServicesByCodEmployee(codEmployee);
    }
}
