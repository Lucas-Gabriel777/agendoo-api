package com.technosdev.repositories;

import com.technosdev.entities.SchedulingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<SchedulingEntity, Long> {
    @Query("SELECT se FROM SchedulingEntity se INNER JOIN ServiceOrderEntity so ON so.schedulingEntity.id = se.id WHERE so.employeeEntity.id = :employeeId AND DATE(se.date) = DATE(:date)")
    List<SchedulingEntity> getSchedulingByEmployeeAndDate(@Param("employeeId") Long employeeId,@Param("date") Date date);
}
