package cn.lee.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/other/")
public class OtherController {

    private static final Logger logger = LoggerFactory.getLogger(OtherController.class);

    @PostMapping(value = "/login")
    public Boolean Login(@RequestParam String userName){
        System.out.println("userName:"+userName);
        logger.info("userName:"+userName);
        return true;
    }

    @RequestMapping("/upload")
    public Boolean upload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] content =file.getBytes();
        logger.info("upload: "+content);
        return true;
    }


}
