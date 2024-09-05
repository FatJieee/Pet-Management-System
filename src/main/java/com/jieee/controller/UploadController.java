package com.jieee.controller;

import com.jieee.pojo.Result;
import com.jieee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private UserService userService;

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
        image.transferTo(new File("D:\\limzh\\VsCode\\Furry\\public\\image" + newFileName));
        return Result.success("/image" + newFileName);
    }
}

