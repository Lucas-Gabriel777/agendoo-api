package com.technosdev.services;

import com.technosdev.dto.CreateCompanyDto;
import com.technosdev.entities.*;
import com.technosdev.repositories.CompanyRepository;
import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlowInformationsService flowInformationsService;

    @Autowired
    private CompanyInformationsService companyInformationsService;

    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }

    public CompanyEntity findById(Long id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);
        return company.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }

    public CompanyEntity findCompanyByUserId(Long id){
        return companyRepository.findCompanyByUserId(id);
    }

    public CompanyEntity insert(CreateCompanyDto createCompanyDto){
        AddressEntity addressEntity = addressService.findById(createCompanyDto.getCodAddress());
        UserEntity userEntity = userService.findById(createCompanyDto.getCodUser());
        FlowInformationsEntity flowInformationsEntity = flowInformationsService.findById(createCompanyDto.getCodFlowInformations());
        CompanyInformationsEntity companyInformationsEntity = companyInformationsService.findById(createCompanyDto.getCodCompanyInformations());

        CompanyEntity companyEntity = new CompanyEntity(addressEntity, userEntity, flowInformationsEntity, companyInformationsEntity, true);
        return companyRepository.save(companyEntity);
    }

    public void delete(Long id){
        try {
            findById(id);
            companyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public CompanyEntity update(Long id, CompanyEntity companyEntity, AddressEntity addressEntity) {
        try {
            CompanyEntity entity = companyRepository.getReferenceById(id);
            updateData(entity, companyEntity, addressEntity);
            return companyRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }

    private void updateData(CompanyEntity entity, CompanyEntity companyEntity, AddressEntity addressEntity) {
        entity.setAddressEntity(addressEntity);
        entity.setUserEntity(companyEntity.getUserEntity());
        entity.setFlowInformationsEntity(companyEntity.getFlowInformationsEntity());
        entity.setCompanyInformationsEntity(companyEntity.getCompanyInformationsEntity());
        entity.setActive(companyEntity.isActive());
    }

    public CompanyEntity findByCodFlowInformations(Long codFlowInformations) {
        Optional<CompanyEntity> company = companyRepository.findByCodFlowInformations(codFlowInformations);
        return company.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }

}
