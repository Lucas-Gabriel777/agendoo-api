package com.technosdev.services;

import com.technosdev.entities.FlowInformationsEntity;
import com.technosdev.repositories.FlowInformationsRepository;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlowInformationsService {
    @Autowired
    private FlowInformationsRepository flowInformationsRepository;

    public FlowInformationsEntity insert(FlowInformationsEntity flowInformationsEntity){
        return flowInformationsRepository.save(flowInformationsEntity);
    }

    public FlowInformationsEntity findById(Long id) {
        Optional<FlowInformationsEntity> flowInformations = flowInformationsRepository.findById(id);
        return flowInformations.orElseThrow(() -> new ResourceNotFoundException("Informações do flow não encontrado"));
    }

    public FlowInformationsEntity findByPhone(String phone) {
        Optional<FlowInformationsEntity> flowInformations = flowInformationsRepository.findByPhone(phone);
        return flowInformations.orElseThrow(() -> new ResourceNotFoundException("Informações do flow não encontrado"));
    }
}
