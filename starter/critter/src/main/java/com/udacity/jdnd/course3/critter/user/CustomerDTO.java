package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getCustomerId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
        this.notes = customer.getNotes();
        if (customer.getPets() != null) {
            this.petIds = customer.getPets().stream().map(pet-> pet.getId()).collect(Collectors.toList());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}
