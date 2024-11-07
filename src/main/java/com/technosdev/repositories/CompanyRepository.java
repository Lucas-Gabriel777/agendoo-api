package com.technosdev.repositories;

import com.technosdev.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    @Query("SELECT ce FROM CompanyEntity ce WHERE ce.flowInformationsEntity.id = :codFlowInformations")
    Optional<CompanyEntity> findByCodFlowInformations(@Param("codFlowInformations") Long codFlowInformations);

    @Query("SELECT cp FROM CompanyEntity cp INNER JOIN EmployeeCompany ec ON cp.id = " +
            "ec.id INNER JOIN EmployeeEntity emp ON emp.id = ec.id WHERE emp.id = :cod_user")
    CompanyEntity findCompanyByUserId(@Param("cod_user") Long cod_user);

}
