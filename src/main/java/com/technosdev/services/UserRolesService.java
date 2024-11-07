package com.technosdev.services;

import com.technosdev.dto.CreateUserRoleDto;
import com.technosdev.entities.RoleEntity;
import com.technosdev.entities.UserEntity;
import com.technosdev.entities.UserRolesEntity;
import com.technosdev.repositories.RoleRepository;
import com.technosdev.repositories.UserRolesRepository;
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
public class UserRolesService {

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public List<UserRolesEntity> findAll() {
        return userRolesRepository.findAll();
    }

    public UserRolesEntity findById(Long id) {
        Optional<UserRolesEntity> userRoles = userRolesRepository.findById(id);
        return userRoles.orElseThrow(() -> new ResourceNotFoundException("Permissão do usuário não encontrada"));
    }

    public UserRolesEntity insert(CreateUserRoleDto createUserRoleDto){
        UserEntity user = userService.findById(createUserRoleDto.getUserId());
        RoleEntity role = roleService.findById(createUserRoleDto.getRoleId());

        UserRolesEntity userRolesEntity = new UserRolesEntity(user, role);
        return userRolesRepository.save(userRolesEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            userRolesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Permissão do usuário não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserRolesEntity update(Long id, UserRolesEntity group) {
        try {
            UserRolesEntity entity = userRolesRepository.getReferenceById(id);
            updateData(entity, group);
            return userRolesRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }

    private void updateData(UserRolesEntity entity, UserRolesEntity role) {
        entity.setRole(role.getRole());
        entity.setUser(role.getUser());
    }

    public List<RoleEntity> findRoleByUserId(Long userId) {
        return userRolesRepository.findRoleByUserId(userId);
    }
}
