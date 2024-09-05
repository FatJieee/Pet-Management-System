package com.jieee.service;

import com.jieee.pojo.PageBean;
import com.jieee.pojo.Pet;

import java.util.List;

public interface PetService {
    PageBean getPetList(Integer pageNum, Integer pageSize);

    void addPet(Pet pet);

    List<Pet> getPetBreed();

    List<Pet> getAdoptionPetList();

    List<Pet> searhAdoption(Integer breedId, String age, String gender, String category);

    List<Pet> getLostPetList();

    List<Pet> searhLost(Integer breedId, String age, String state, String category);

    Pet getPetById(Integer id);

    PageBean getPetListById(Integer pageNum, Integer pageSize, Integer userId);

    void updateStatus(Pet pet);

    PageBean getLostPetWithParam(Integer pageNum, Integer pageSize);

    void verifyPet(Pet pet);

    void deletePet(Integer id);

    PageBean getAdoptPetWithParam(Integer pageNum, Integer pageSize);
}
