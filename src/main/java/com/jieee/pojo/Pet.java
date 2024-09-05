package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private Integer id;
    private Integer userId;
    private Integer petId;
    private Integer breedId;
    private String userName;
    private String contact;
    private String email;
    private String breedName;
    private String petCategory;
    private String name;
    private String image;
    private String userImage;
    private Integer age;
    private Integer minAge;
    private Integer maxAge;
    private String gender;
    private String category;
    private String health;
    private String vaccination;
    private String neutering;
    private String location;
    private String detail;
    private Double reward;
    private Double fee;
    private String type;
    private String status;
    private String state;
    private String verify;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
