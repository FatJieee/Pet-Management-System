package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String jwt;
    private String password;
    private String rePassword;
    private String newPass;
    private String reNewPass;
    private String image;
    private String contact;
    private String address;
    private String gender;
    private Integer age;
    private String email;
    private String state;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
