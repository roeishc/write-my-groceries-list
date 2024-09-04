package com.handson.write_my_groceries_list.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.handson.write_my_groceries_list.repo.S3BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/image")
public class AwsController {

    private static final Logger logger = LoggerFactory.getLogger(AwsController.class);

    @Autowired
    S3BucketService s3BucketService;


    @GetMapping("{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName){
        byte[] imageBytes = null;
        try{
            imageBytes = s3BucketService.downloadImage(fileName);
        }
        catch (AmazonS3Exception e){    // image not found in s3
            if (e.getStatusCode() == 404){
                logger.debug("Requested image not found: " + fileName);
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            }
        }
        if (imageBytes == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(S3BucketService.determineContentType(fileName)));
        return new ResponseEntity<>(imageBytes, httpHeaders, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> uploadReceiptImage(@RequestParam("image") MultipartFile image) throws IOException {
        String imageUrl = s3BucketService.uploadImage(image, S3BucketService.generateFileName(image));
        if (imageUrl == null){
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

}
