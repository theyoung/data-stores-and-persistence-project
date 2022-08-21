package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    EmployeeService employeeService;
    ScheduleService scheduleService;
    PetService petService;
    UserService userService;

    public ScheduleController(EmployeeService employeeService, ScheduleService scheduleService, PetService petService, UserService userService) {
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        List<Employee> employees = employeeService.getAllEmployeesByEmployeeIds(employeeIds);
        if(employees.size() != employeeIds.size()) {
            throw new IllegalArgumentException("One or more Employee do not exist");
        }

        DayOfWeek dayOfWeek = scheduleDTO.getDate().getDayOfWeek();

        employees.stream().forEach(employee -> {
            if(!employee.getDaysAvailable().contains(dayOfWeek)) {
                throw new IllegalArgumentException(employee.getName() + " is not available on " + dayOfWeek);
            }
        });


        List<Pet> pets = petService.getAllPetsByPetIds(petIds);
        if(pets.size() != petIds.size()) {
            throw new IllegalArgumentException("One or more pets do not exist");
        }

        Schedule schedule = getScheduleFromScheduleDTO(scheduleDTO);
        return new ScheduleDTO(scheduleService.saveSchedule(schedule));
    }

    public Schedule getScheduleFromScheduleDTO(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setEmployeeIds(scheduleDTO.getEmployeeIds());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPetIds(scheduleDTO.getPetIds());

        return schedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAll().stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findByPetId(petId).stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findByEmployeeId(employeeId).stream().map(ScheduleDTO::new).collect(Collectors.toList());

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = userService.getCustomerById(customerId);
        if(customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        List<Pet> pets = customer.getPets();
        List<Long> petIds = pets.stream().map(Pet::getId).collect(Collectors.toList());

        return scheduleService.findByPetIds(petIds).stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }
}
