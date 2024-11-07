package com.technosdev.services;

import com.technosdev.entities.SchedulingEntity;
import com.technosdev.repositories.SchedulingRepository;
import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulingService {
    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<SchedulingEntity> findAll() {
        return schedulingRepository.findAll();
    }

    public SchedulingEntity findById(Long id) {
        Optional<SchedulingEntity> employee = schedulingRepository.findById(id);
        return employee.orElseThrow(() -> new ResourceNotFoundException("Agendamento do colaborador não encontrado"));
    }

    public SchedulingEntity insert(SchedulingEntity schedulingEntity){
        return schedulingRepository.save(schedulingEntity);
    }

    public void delete(Long id) {
        try {
            findById(id);
            schedulingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Agendamento do colaborador não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public SchedulingEntity update(Long id, SchedulingEntity schedulingEntity) {
        try {
            SchedulingEntity entity = schedulingRepository.getReferenceById(id);
            updateData(entity, schedulingEntity);
            return schedulingRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }

    private void updateData(SchedulingEntity entity, SchedulingEntity schedulingEntity) {
        entity.setClient(schedulingEntity.getClient());
        entity.setSchedulingStatus(schedulingEntity.getSchedulingStatus());
    }

    public List<SchedulingEntity> getSchedulingByEmployeeAndDate(Long employeeId, Date date) {
        return schedulingRepository.getSchedulingByEmployeeAndDate(employeeId, date);
    }

}
