package com.handson.write_my_groceries_list.controller;


import com.handson.write_my_groceries_list.aws.S3BucketService;
import com.handson.write_my_groceries_list.model.Receipt;
import com.handson.write_my_groceries_list.model.ReceiptOut;
import com.handson.write_my_groceries_list.repo.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private S3BucketService s3BucketService;


    @GetMapping(path = "/receipt")
    public ResponseEntity<ReceiptOut> getReceiptByReceiptId(String stringId){
        UUID receiptId = UUID.fromString(stringId);
        Optional<Receipt> receipt = receiptService.findById(receiptId);
        if (receipt.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        ReceiptOut response = ReceiptOut.of(receipt.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/test")
    public ResponseEntity<?> getTest(){
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

}