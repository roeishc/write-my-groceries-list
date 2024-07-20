package com.handson.write_my_groceries_list.controller;


import com.handson.write_my_groceries_list.aws.S3BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    S3BucketService s3BucketService;

    @PostMapping("/image")
    public ResponseEntity<?> uploadReceiptImage(@RequestParam("image")MultipartFile image){
        String imageUrl = s3BucketService.uploadImage(image);
        if (imageUrl == null){
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    @GetMapping(path = "/helloWorld")
    public String helloWorld(){
        return "Hello, World!";
    }

}
