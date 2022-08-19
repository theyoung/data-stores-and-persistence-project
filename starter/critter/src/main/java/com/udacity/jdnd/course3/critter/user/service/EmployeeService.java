package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Employee findByEmployeeId(Long employeeId) {
        return repository.findById(employeeId).get();
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = repository.findById(employeeId).get();
        employee.setDaysAvailable(daysAvailable);
        repository.save(employee);
    }

    public List<Employee> findAllByDaysAvailableContainsAndSkills(DayOfWeek day, Set<EmployeeSkill> skills) {
        List<Employee> employees = repository.findAllBydaysAvailableContains(day);

        return employees.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
