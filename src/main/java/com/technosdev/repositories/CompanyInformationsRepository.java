package com.technosdev.repositories;

import com.technosdev.entities.CompanyInformationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyInformationsRepository extends JpaRepository<CompanyInformationsEntity, Long> {
    Optional<CompanyInformationsEntity> findByEmail(String email);
    Optional<CompanyInformationsEntity> findByPhone(String phone);
}
