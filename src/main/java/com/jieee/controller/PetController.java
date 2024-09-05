package com.jieee.controller;


import com.jieee.pojo.PageBean;
import com.jieee.pojo.Pet;

import com.jieee.pojo.Result;
import com.jieee.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/pet")
@RestController
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/getPetList")
    public Result getPetList(Integer pageNum, Integer pageSize){
        PageBean pageBean = petService.getPetList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("image: {}",image);
        //get file name
        String oriName = image.getOriginalFilename();
        int index = oriName.lastIndexOf(".");
        String extname = oriName.substring(index);

        //generate new file name
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("new file name: {}",newFileName);

        //save file to assets/image in front end
        image.transferTo(new File("D:\\limzh\\VsCode\\Furry\\public\\image\\pet" + newFileName));
        return Result.success("/image/pet" + newFileName);
    }

    @GetMapping("/getPetBreed")
    public Result getPetBreed(){
        List<Pet> pets = petService.getPetBreed();
        return Result.success(pets);
    }

    @PostMapping("/addPet")
    public Result addProduct(@RequestBody Pet pet){
        log.info("pet {}",pet);

        if(pet.getBreedId() == 0 || pet.getAge() == 0 || pet.getGender() == ""
                || pet.getName() == ""){
            return Result.error("All column must be selected");
        }

        if ("adoption".equalsIgnoreCase(pet.getType())) {
            if (pet.getHealth() == "" || pet.getNeutering() == "" || pet.getVaccination() == "") {
                return Result.error("Adoption detail must be select.");
            }
        } else if ("lost".equalsIgnoreCase(pet.getType())) {
            if (pet.getLocation() == "") {
               return Result.error("Lost location must be select.");
            }
        }
        petService.addPet(pet);
        return Result.success();
    }

    @GetMapping("/getAdoptionPetList")
    public Result getAdoptionPetList(){
        List<Pet> pets = petService.getAdoptionPetList();
        return Result.success(pets);
    }

    @GetMapping("/searhAdoption")
    public Result searhAdoption(
            @RequestParam(required = false) Integer breedId,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String category
    ){
        List<Pet> pets = petService.searhAdoption(breedId,age,gender,category);
        return Result.success(pets);
    }

    @GetMapping("/getLostPetList")
    public Result getLostPetList(){
        List<Pet> pets = petService.getLostPetList();
        return Result.success(pets);
    }

    @GetMapping("/searhLost")
    public Result searhLost(
            @RequestParam(required = false) Integer breedId,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String category
    ){
        List<Pet> pets = petService.searhLost(breedId,age,state,category);
        return Result.success(pets);
    }

    @GetMapping("/getPetById")
    public Result getPetById(Integer id){
        log.info("pet: {}",id);
        Pet pets = petService.getPetById(id);
        return Result.success(pets);
    }

    @GetMapping("/getPetListById")
    public Result getPetListById(Integer pageNum, Integer pageSize, Integer userId){
        log.info("petlist: {}",userId);
        PageBean pageBean = petService.getPetListById(pageNum,pageSize,userId);
        return Result.success(pageBean);
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody Pet pet){
        log.info("pet: {}",pet);
        petService.updateStatus(pet);
        return Result.success();
    }

    @GetMapping("/getLostPetWithParam")
    public Result getLostPetWithParam(Integer pageNum, Integer pageSize){
        PageBean pageBean = petService.getLostPetWithParam(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @PostMapping("/verifyPet")
    public Result verifyPet(@RequestBody Pet pet){
        log.info("pet: {}",pet);
        petService.verifyPet(pet);
        return Result.success();
    }

    @PostMapping("/deletePet")
    public Result deletePet(@RequestBody Map<String, Integer> pet){
        Integer id = pet.get("id");
        log.info("postid: {}",id);
        petService.deletePet(id);
        return Result.success();
    }

    @GetMapping("/getAdoptPetWithParam")
    public Result getAdoptPetWithParam(Integer pageNum, Integer pageSize){
        PageBean pageBean = petService.getAdoptPetWithParam(pageNum,pageSize);
        return Result.success(pageBean);
    }
}
