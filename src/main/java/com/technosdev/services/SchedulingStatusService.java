package com.technosdev.services;

import com.technosdev.entities.SchedulingStatusEntity;
import com.technosdev.repositories.SchedulingStatusRepository;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulingStatusService {
    @Autowired
    private SchedulingStatusRepository schedulingStatusRepository;

    public List<SchedulingStatusEntity> findAll() {
        return schedulingStatusRepository.findAll();
    }

    public SchedulingStatusEntity findById(Long id){
        Optional<SchedulingStatusEntity> schedulingStatus = schedulingStatusRepository.findById(id);
        return schedulingStatus.orElseThrow(() -> new ResourceNotFoundException("Status não encontrado"));
    }

    public SchedulingStatusEntity insert(SchedulingStatusEntity schedulingStatusEntity){
        schedulingStatusEntity.setActive(true);
        return schedulingStatusRepository.save(schedulingStatusEntity);
    }

}
