package com.handson.write_my_groceries_list.chatgpt;

import com.handson.write_my_groceries_list.aws.S3BucketService;
import com.handson.write_my_groceries_list.jwt.DBUser;
import com.handson.write_my_groceries_list.jwt.DBUserService;
import com.handson.write_my_groceries_list.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.handson.write_my_groceries_list.util.Util.getUserName;

@RestController
@RequestMapping("/api/gpt")
public class GptController {

    private static final Logger logger = LoggerFactory.getLogger(GptService.class);

    @Autowired
    private GptService gptService;

    @Autowired
    private S3BucketService s3BucketService;

    @Autowired
    private DBUserService dbUserService;


    @GetMapping
    public ResponseEntity<?> getInfoAboutReceipt(HttpServletRequest request, @RequestParam String receiptId){
        Optional<DBUser> dbUser = dbUserService.findUserName(getUserName(request));
        if (dbUser.isEmpty()){
            logger.error("User not found in database; request:\n" + request);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String tempLink = s3BucketService.generateLink(
                S3BucketService.getFullPathInBucket(dbUser.get().getName(), receiptId)
        );

        String res = gptService.testing("What is in this image?", tempLink);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
