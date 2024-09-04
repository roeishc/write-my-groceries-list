package com.handson.write_my_groceries_list.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);


    @GetMapping(path = "/helloWorld")
    public String helloWorld(){
        return "Hello, World!";
    }

}
