package com.technosdev.repositories;

import com.technosdev.entities.FlowInformationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowInformationsRepository extends JpaRepository<FlowInformationsEntity, Long> {
    Optional<FlowInformationsEntity> findByPhone(String phone);
}
