package com.handson.write_my_groceries_list.controller;


import com.handson.write_my_groceries_list.aws.S3BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    S3BucketService s3BucketService;


    @GetMapping(path = "/helloWorld")
    public String helloWorld(){
        return "Hello, World!";
    }

}
