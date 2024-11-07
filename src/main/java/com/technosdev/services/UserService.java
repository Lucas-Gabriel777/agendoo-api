package com.technosdev.services;

import com.technosdev.dto.CreateUserDto;
import com.technosdev.dto.CreateUserRoleDto;
import com.technosdev.entities.RoleEntity;
import com.technosdev.entities.UserEntity;
import com.technosdev.entities.UserRolesEntity;
import com.technosdev.repositories.RoleRepository;
import com.technosdev.repositories.UserRepository;
import com.technosdev.repositories.UserRolesRepository;
import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import com.technosdev.services.exceptions.SQLIntegrityConstraintViolationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private RoleService roleService;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        Optional<UserEntity> client = userRepository.findById(id);
        return client.orElseThrow(() -> new ResourceNotFoundException("Usuário não foi encontrado"));
    }

    @Transactional
    public UserEntity insert(CreateUserDto createUserDto) {
        try {
            String passwordEncryption = null;

            if (createUserDto.getPassword() != null) {
                passwordEncryption = passwordEncoder.encode(createUserDto.getPassword());
            }

            UserEntity userEntity = new UserEntity(createUserDto.getName(), createUserDto.getEmail(), passwordEncryption, createUserDto.getCpf(), createUserDto.getPhone(), true);
            userEntity = userRepository.save(userEntity);

            createUserRoles(createUserDto, userEntity);

            return userEntity;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private void createUserRoles(CreateUserDto createUserDto, UserEntity userEntity) {
        RoleEntity roleEntity = roleService.findById(createUserDto.getRoleId());

        UserRolesEntity userRolesEntity = new UserRolesEntity(userEntity, roleEntity);
        userRolesRepository.save(userRolesEntity);

        List<RoleEntity> rolesList = new ArrayList<>();
        rolesList.add(roleEntity);

        userEntity.setRoles(rolesList);
    }

    public void delete(Long id) {
        try {
            findById(id);
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserEntity update(Long id, UserEntity userEntity) {
        try {
            UserEntity entity = userRepository.getReferenceById(id);
            updateData(entity, userEntity);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

    private void updateData(UserEntity entity, UserEntity userEntity) {
        entity.setName(userEntity.getName());
        entity.setName(userEntity.getName());
        entity.setEmail(userEntity.getEmail());
        entity.setActive(userEntity.isActive());
        entity.setCpf(userEntity.getCpf());
    }

    public UserEntity getUserPermission(String email){
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            return null;
        }

        UserEntity userEntity = userOptional.get();
        List<RoleEntity> roleEntities = userRolesRepository.findRoleByUserId(userEntity.getId());

        userEntity.setRoles(roleEntities);

        return userEntity;
    }

    public UserEntity findByPhone(String phone) {
        return userRepository.findByPhone(phone)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public UserEntity findByCPF(String cpf) {
        return userRepository.findByCpf(cpf).orElse(null);
    }
}
