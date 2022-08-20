package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByEmployeeIds(long employeeId);

    List<Schedule> findAllByPetIds(long petId);

    List<Schedule> findAllByPetIdsIn(List<Long> petIds);

//    @Query("select s from Schedule s join s.petIds ids where ids = :petId")
//    List<Schedule> findAllByPetId(long petId);
//
//    @Query("select s from Schedule s join s.employeeIds ids where ids = :employeeId")
//    List<Schedule> findAllByEmployeeId(long employeeId);
}
