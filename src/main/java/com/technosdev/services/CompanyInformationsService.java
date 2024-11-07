package com.technosdev.services;

import com.technosdev.dto.CreateCompanyInformationsDto;
import com.technosdev.entities.CompanyInformationsEntity;
import com.technosdev.repositories.CompanyInformationsRepository;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyInformationsService {
    @Autowired
    private CompanyInformationsRepository companyInformationsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<CompanyInformationsEntity> findAll() {
        return companyInformationsRepository.findAll();
    }

    public CompanyInformationsEntity insert(@Valid CreateCompanyInformationsDto createCompanyInformationsDto) {
        String passwordEncryption = passwordEncoder.encode(createCompanyInformationsDto.getPassword());

        LocalTime startTime = LocalTime.parse(createCompanyInformationsDto.getStartTime());
        LocalTime endTime = LocalTime.parse(createCompanyInformationsDto.getEndTime());

        CompanyInformationsEntity companyInformationsEntity = new CompanyInformationsEntity();
        companyInformationsEntity.setName(createCompanyInformationsDto.getName());
        companyInformationsEntity.setCnpj(createCompanyInformationsDto.getCnpj());
        companyInformationsEntity.setEmail(createCompanyInformationsDto.getEmail());
        companyInformationsEntity.setPhone(createCompanyInformationsDto.getPhone());
        companyInformationsEntity.setPassword(passwordEncryption);
        companyInformationsEntity.setStartTime(Time.valueOf(startTime));
        companyInformationsEntity.setEndTime(Time.valueOf(endTime));
        companyInformationsEntity.setAverageTimeWork(createCompanyInformationsDto.getAverageTimeWork());

        return companyInformationsRepository.save(companyInformationsEntity);
    }

    public CompanyInformationsEntity findById(Long id) {
        Optional<CompanyInformationsEntity> companyInformationsEntity = companyInformationsRepository.findById(id);
        return companyInformationsEntity.orElseThrow(() -> new ResourceNotFoundException("Informações da empresa não encontradas"));
    }
}
