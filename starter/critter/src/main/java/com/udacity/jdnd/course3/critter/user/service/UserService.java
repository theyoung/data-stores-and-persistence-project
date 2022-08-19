package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    CustomerRepository repository;

    public UserService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = repository.findAll();
        return list;
    }

    public Customer getCustomerById(long customerId) {
        return repository.findById(customerId).orElse(null);
    }
}
