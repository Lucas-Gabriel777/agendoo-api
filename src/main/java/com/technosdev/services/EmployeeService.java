package com.technosdev.services;

import com.technosdev.dto.CreateUserDto;
import com.technosdev.entities.EmployeeEntity;
import com.technosdev.entities.UserEntity;
import com.technosdev.enums.UserRolesEnum;
import com.technosdev.repositories.EmployeeRepository;
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
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    public EmployeeEntity findById(Long id) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
    }

    public EmployeeEntity insert(EmployeeEntity employeeEntity) {
        UserEntity userEntity = employeeEntity.getUserEntity();

        CreateUserDto createUserDto = new CreateUserDto(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getCpf(), userEntity.getPhone(), UserRolesEnum.EMPLOYEE);

        userEntity = userService.insert(createUserDto);
        employeeEntity.setUserEntity(userEntity);

        return employeeRepository.save(employeeEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public EmployeeEntity update(Long id, EmployeeEntity employeeEntity) {
        try {
            EmployeeEntity entity = employeeRepository.getReferenceById(id);
            updateData(entity, employeeEntity);
            return employeeRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }

    private void updateData(EmployeeEntity entity, EmployeeEntity employeeEntity) {
        entity.setHiredIn(employeeEntity.getHiredIn());
        entity.setUserEntity(employeeEntity.getUserEntity());
        entity.setActive(employeeEntity.isActive());
    }

}
