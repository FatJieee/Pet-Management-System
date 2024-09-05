package com.jieee.mapper;

import com.jieee.pojo.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PetMapper {

    List<Pet> getPetList();

    void addPet(Pet pet);

    void addAdoption(Pet pet);

    void addLost(Pet pet);

    @Select("select * from furry.pet_breed")
    List<Pet> getPetBreed();

    List<Pet> getAdoptionPetList();

    List<Pet> searhAdoption(Pet pet);

    List<Pet> getLostPetList();

    List<Pet> searhLost(Pet pet);

    Pet getPetById(Integer id);

    Pet getAdoptionByPetId(Integer id);

    Pet getLostByPetId(Integer id);


    List<Pet> getPetListById(Integer userId);

    @Update("update furry.pet set type = 'Adopted', update_time = #{updateTime} where id = #{id}")
    void updateStatusPet1(Pet pet);

    @Update("update furry.pet set type = 'Found', update_time = #{updateTime} where id = #{id}")
    void updateStatusPet2(Pet pet);

    List<Pet> getLostPetWithParam();

    @Update("update furry.pet set verify = #{verify} where id = #{id}")
    void verifyPet(Pet pet);

    @Delete("delete from furry.pet where id = #{id}")
    void deletePet(Integer id);

    List<Pet> getAdoptPetWithParam();
}
