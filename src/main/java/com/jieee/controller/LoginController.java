package com.jieee.controller;

import com.jieee.pojo.Result;
import com.jieee.pojo.User;
import com.jieee.service.UserService;
import com.jieee.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("Login user: {}",user);
        User e = userService.login(user);
        log.info("e: {}",e);

        if(e != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            claims.put("email",e.getEmail());

            String jwt = JwtUtils.generateJwt(claims);
            e.setJwt(jwt);
            return Result.success(e);
        }
        return Result.error("Username or Password invalid");
    }
}
