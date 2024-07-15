package com.handson.write_my_groceries_list.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping(path = "/helloWorld")
    public String helloWorld(){
        return "Hello, World!";
    }

}
