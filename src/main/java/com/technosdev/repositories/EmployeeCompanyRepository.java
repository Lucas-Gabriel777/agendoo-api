package com.technosdev.repositories;

import com.technosdev.entities.EmployeeCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCompanyRepository extends JpaRepository<EmployeeCompany, Long> {
    @Query("SELECT ec FROM EmployeeCompany ec WHERE ec.companyEntity.id = :codCompany")
    List<EmployeeCompany> findByCodCompany(@Param("codCompany") Long codCompany);
}
