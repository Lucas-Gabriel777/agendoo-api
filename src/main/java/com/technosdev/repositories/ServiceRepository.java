package com.technosdev.repositories;

import com.technosdev.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    @Query("SELECT s FROM ServiceEntity s INNER JOIN CompanyEntity cp ON cp.id = s.id WHERE s.id = :codCompany")
    List<ServiceEntity> findAllByCompanyId(@Param("codCompany") Long codCompany);

}
