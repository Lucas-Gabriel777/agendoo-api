package com.technosdev.repositories;

import com.technosdev.entities.ServiceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrderEntity, Long> {
}
