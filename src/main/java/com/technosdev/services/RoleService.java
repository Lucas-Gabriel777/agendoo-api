package com.technosdev.services;

import com.technosdev.entities.RoleEntity;
import com.technosdev.repositories.RoleRepository;
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
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public RoleEntity findById(Long id) {
        Optional<RoleEntity> role = roleRepository.findById(id);
        return role.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
    }

    public RoleEntity insert(RoleEntity roleEntity){
        return roleRepository.save(roleEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            roleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Regra não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public RoleEntity update(Long id, RoleEntity roleEntity) {
        try {
            RoleEntity entity = roleRepository.getReferenceById(id);
            updateData(entity, roleEntity);
            return roleRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }

    private void updateData(RoleEntity entity, RoleEntity roleEntity) {
        entity.setName(roleEntity.getName());
    }

}
