package com.handson.write_my_groceries_list.controller;


import com.handson.write_my_groceries_list.aws.S3BucketService;
import com.handson.write_my_groceries_list.jwt.DBUser;
import com.handson.write_my_groceries_list.jwt.DBUserService;
import com.handson.write_my_groceries_list.jwt.JwtTokenUtil;
import com.handson.write_my_groceries_list.model.Receipt;
import com.handson.write_my_groceries_list.model.ReceiptOut;
import com.handson.write_my_groceries_list.repo.ReceiptService;
import com.handson.write_my_groceries_list.util.Dates;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.handson.write_my_groceries_list.util.Util.getUserName;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private S3BucketService s3BucketService;

    @Autowired
    private DBUserService dbUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping(path = "/{receiptId}")
    public ResponseEntity<ReceiptOut> getReceiptByReceiptId(@PathVariable  String receiptId){
        Optional<Receipt> receipt;
        try{
            receipt = receiptService.findById(UUID.fromString(receiptId));
        }
        catch (IllegalArgumentException e){
            logger.warn("Invalid UUID: " + receiptId +  "\n" + e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (receipt.isEmpty() || !receipt.get().isActive()){
            logger.warn("No receipt exists with ID: " + receiptId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ReceiptOut.of(receipt.get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReceipt(HttpServletRequest request,
                                           @RequestParam MultipartFile image,
                                           @RequestParam int totalCost) {
        Optional<DBUser> dbUser = dbUserService.findUserName(getUserName(request));
        if (dbUser.isEmpty()){
            logger.error("User not found in database; request:\n" + request);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Receipt receipt = new Receipt(
                dbUser.get(),
                image.getOriginalFilename(),
                totalCost,
                true,
                Dates.nowUTC()
        );
        receipt.generateUUID(); // required for creating the path in s3
        try {
            s3BucketService.uploadImage(image, S3BucketService.getImagePath(receipt));
        }
        catch (IOException e){
            logger.warn(e.getMessage() + ". image:\n" + image);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        receipt.setFullPathInBucket(S3BucketService.getFullPathInBucket(S3BucketService.getImagePath(receipt)));
        receiptService.save(receipt);
        return new ResponseEntity<>(ReceiptOut.of(receipt), HttpStatus.CREATED);

    }

    @GetMapping(path = "/getAllReceiptsByUser")
    public ResponseEntity<List<ReceiptOut>> getAllReceipts(HttpServletRequest request){
        Optional<DBUser> dbUser = dbUserService.findUserName(getUserName(request));
        if (dbUser.isEmpty()){
            logger.error("User not found in database; request:\n" + request);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Iterable<Receipt> receipts = receiptService.findReceiptsByUserName(dbUser.get().getName());
        List<ReceiptOut> receiptOuts = new ArrayList<>();
        receipts.forEach(receipt -> {
            if (receipt.isActive())
                receiptOuts.add(ReceiptOut.of(receipt));
        });
        return new ResponseEntity<>(receiptOuts, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{receiptId}")
    public ResponseEntity<?> deleteReceiptByReceiptId(@PathVariable String receiptId){
        Optional<Receipt> receipt;
        try{
            receipt = receiptService.findById(UUID.fromString(receiptId));
        }
        catch (IllegalArgumentException e){
            logger.warn("Invalid UUID: " + receiptId +  "\n" + e);
            return new ResponseEntity<>("Invalid ID: " + receiptId, HttpStatus.BAD_REQUEST);
        }
        if (receipt.isEmpty() || !receipt.get().isActive()){
            logger.warn("No receipt exists with ID: " + receiptId);
            return new ResponseEntity<>("No receipt exists with ID: " + receiptId, HttpStatus.NOT_FOUND);
        }
        receipt.get().setActive(false);
        receiptService.save(receipt.get());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteReceipt(@PathVariable String receiptId){
        Optional<Receipt> receipt;
        try{
            receipt = receiptService.findById(UUID.fromString(receiptId));
        }
        catch (IllegalArgumentException e){
            logger.warn("Invalid UUID: " + receiptId +  "\n" + e);
            return new ResponseEntity<>("Invalid ID: " + receiptId, HttpStatus.BAD_REQUEST);
        }
        if (receipt.isEmpty() || !receipt.get().isActive()){
            logger.warn("No receipt exists with ID: " + receiptId);
            return new ResponseEntity<>("No receipt exists with ID: " + receiptId, HttpStatus.NOT_FOUND);
        }
        s3BucketService.deleteImage(S3BucketService.getImagePath(receipt.get()));
        receiptService.deleteById(receipt.get().getId());
        return new ResponseEntity<>("Deleted receipt ID: " + receiptId, HttpStatus.OK);
    }

}
