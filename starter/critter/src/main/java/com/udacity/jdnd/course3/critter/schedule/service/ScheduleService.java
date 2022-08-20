package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ScheduleService {

    ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return repository.save(schedule);
    }

    public List<Schedule> getAll() {
        return repository.findAll();
    }

    public List<Schedule> findByPetId(long petId) {
        return repository.findAllByPetIds(petId);
    }

    public List<Schedule> findByEmployeeId(long employeeId) {
        return repository.findAllByEmployeeIds(employeeId);
    }

    public List<Schedule> findByPetIds(List<Long> pets) {
        return repository.findAllByPetIdsIn(pets);
    }
}
