package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    PetService petService;
    EmployeeService employeeService;

    public UserController(UserService service, PetService petService, EmployeeService employeeService) {
        this.userService = service;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());

        customer = userService.saveCustomer(customer);
        return new CustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.getAllCustomers().stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable long customerId){
        return new CustomerDTO(userService.getCustomerById(customerId));
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = petService.getOwnerByPetID(petId);
        if (customer == null) {
            throw new IllegalArgumentException("Pet id does not exist");
        }

        return new CustomerDTO(customer);
    }


    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        var employee = getEmployeefromEmployeeDTO(employeeDTO);
        employee = employeeService.save(employee);
        return new EmployeeDTO(employee);
    }

    private Employee getEmployeefromEmployeeDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return new EmployeeDTO(employeeService.findByEmployeeId(employeeId));
    }


    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.findAllByDaysAvailableContainsAndSkills(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills()).stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

}
