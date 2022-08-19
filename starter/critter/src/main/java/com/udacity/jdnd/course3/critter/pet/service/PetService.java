package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PetService {
    PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Pet savePet(Pet pet) {
        return repository.save(pet);
    }

    public List<Pet> getAllPets() {
        return (List<Pet>) repository.findAll();
    }

    public List<Pet> getAllPetsByOwnerId(Long ownerId){
        return repository.findAllByOwnerId(ownerId);

    }

    public Pet getPetById(long petId) {
        return repository.findById(petId).orElse(null);
    }

    public Customer getOwnerByPetID(Long petId){
        return repository.getCustomerByPetId(petId);
    }
}
