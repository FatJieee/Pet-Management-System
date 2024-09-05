package com.jieee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jieee.mapper.PetMapper;
import com.jieee.pojo.PageBean;
import com.jieee.pojo.Pet;
import com.jieee.service.PetService;
import com.jieee.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;

    @Override
    public PageBean getPetList(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Pet> pets = petMapper.getPetList();
        Page<Pet> p = (Page<Pet>) pets;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPet(Pet pet) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        pet.setUserId(userid);
        pet.setCreateTime(LocalDateTime.now());
        pet.setUpdateTime(LocalDateTime.now());
        petMapper.addPet(pet);

        if ("adoption".equalsIgnoreCase(pet.getType())) {
            pet.setStatus("Available");
            log.info("adoption: {}", pet);
            petMapper.addAdoption(pet);
        } else if ("lost".equalsIgnoreCase(pet.getType())) {
            pet.setStatus("Lost");
            log.info("lost: {}", pet);
            petMapper.addLost(pet);
        }
    }

    @Override
    public List<Pet> getPetBreed() {
        return petMapper.getPetBreed();
    }

    @Override
    public List<Pet> getAdoptionPetList() {
        return petMapper.getAdoptionPetList();
    }

    @Override
    public List<Pet> searhAdoption(Integer breedId, String age, String gender, String category) {
        Pet pet = new Pet();
        pet.setBreedId(breedId);
        pet.setGender(gender);
        pet.setCategory(category);

        if("Kitten".equalsIgnoreCase(age)){
            pet.setMinAge(1);
            pet.setMaxAge(6);
        }else if("Young".equalsIgnoreCase(age)){
            pet.setMinAge(7);
            pet.setMaxAge(23);
        }else if("Adult".equalsIgnoreCase(age)){
            pet.setMinAge(24);
            pet.setMaxAge(84);
        }else if("Senior".equalsIgnoreCase(age)){
            pet.setMinAge(85);
        }
        return petMapper.searhAdoption(pet);
    }

    @Override
    public List<Pet> getLostPetList() {
        return petMapper.getLostPetList();
    }

    @Override
    public List<Pet> searhLost(Integer breedId, String age, String state, String category) {
        Pet pets = new Pet();
        pets.setBreedId(breedId);
        pets.setState(state);
        pets.setCategory(category);

        if("Kitten".equalsIgnoreCase(age)){
            pets.setMinAge(1);
            pets.setMaxAge(6);
        }else if("Young".equalsIgnoreCase(age)){
            pets.setMinAge(7);
            pets.setMaxAge(23);
        }else if("Adult".equalsIgnoreCase(age)){
            pets.setMinAge(24);
            pets.setMaxAge(84);
        }else if("Senior".equalsIgnoreCase(age)){
            pets.setMinAge(85);
        }
        log.info("Pet:{]", pets);
        return petMapper.searhLost(pets);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Pet getPetById(Integer id) {

        Pet pets = petMapper.getPetById(id);
        pets.setPetId(id);
        log.info("Pet:{}", pets);

        if ("Adoption".equalsIgnoreCase(pets.getType())) {
            Pet pet1 = petMapper.getAdoptionByPetId(id);
            pets.setFee(pet1.getFee());
            pets.setDetail(pet1.getDetail());
            pets.setHealth(pet1.getHealth());
            pets.setVaccination(pet1.getVaccination());
            pets.setNeutering(pet1.getNeutering());
        } else if ("Lost".equalsIgnoreCase(pets.getType())) {
            Pet pet2 = petMapper.getLostByPetId(id);
            pets.setDetail(pet2.getDetail());
            pets.setLocation(pet2.getLocation());
            pets.setReward(pet2.getReward());
        }
        return pets;
    }

    @Override
    public PageBean getPetListById(Integer pageNum, Integer pageSize, Integer userId) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Pet> pets = petMapper.getPetListById(userId);
        Page<Pet> p = (Page<Pet>) pets;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(Pet pet) {
        pet.setUpdateTime(LocalDateTime.now());
        if("Adoption".equalsIgnoreCase(pet.getType())){
            petMapper.updateStatusPet1(pet);
//            petMapper.updateStatusAdoption(pet);
        }else if("Lost".equalsIgnoreCase(pet.getType())){
//            petMapper.updateStatusLost(pet);
            petMapper.updateStatusPet2(pet);
        }
    }

    @Override
    public PageBean getLostPetWithParam(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Pet> pets = petMapper.getLostPetWithParam();
        Page<Pet> p = (Page<Pet>) pets;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void verifyPet(Pet pet) {
        petMapper.verifyPet(pet);
    }

    @Override
    public void deletePet(Integer id) {
        petMapper.deletePet(id);
    }

    @Override
    public PageBean getAdoptPetWithParam(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Pet> pets = petMapper.getAdoptPetWithParam();
        Page<Pet> p = (Page<Pet>) pets;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }
}
