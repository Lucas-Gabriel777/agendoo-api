package com.technosdev.repositories;

import com.technosdev.entities.EmployeeServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeServiceRepository extends JpaRepository<EmployeeServiceEntity, Long> {
    @Query("SELECT ese FROM EmployeeServiceEntity ese WHERE ese.employeeEntity.id = :codEmployee")
    List<EmployeeServiceEntity> listServicesByCodEmployee(@Param("codEmployee") Long codEmployee);
}
