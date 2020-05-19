package com.kokotripadmin.controller;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kokotripadmin.exception.amazon_s3_bucket.FileIsNotImageException;
import com.kokotripadmin.exception.amazon_s3_bucket.ImageDuplicateException;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
public class BucketController {

    @Autowired
    private BucketService bucketService;

    @Autowired
    private Convert convert;

    @PostMapping(value = "upload/image", consumes = {"multipart/form-data"}, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> uploadImageToS3Bucket(@Valid @RequestParam("image") MultipartFile multipartFile,
                                                        @Valid @RequestParam("directory") String directory,
                                                        @Valid @RequestParam("fileName") String fileName) {
        try {
            String endPoint = bucketService.uploadImage(directory, fileName, multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(endPoint));
        } catch (SdkClientException | IOException | ImageDuplicateException | FileIsNotImageException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(convert.exceptionToJson(exception.getMessage()));
        }
    }

}



//
//
//
//        int x = 10;
//
//        try {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("test error");
////            Files.write(Paths.get("/kokotripadmin/test"), multipartFile.getBytes());
//
//            System.out.println(multipartFile.getName());
//            Path filepath = Paths
//                    .get("C:\\Users\\mtae\\Downloads", multipartFile.getOriginalFilename() + suffix + ".png");
//            suffix++;
//
//            try (OutputStream os = Files.newOutputStream(filepath)) {
//                os.write(multipartFile.getBytes());
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("x: " + x);
//        return null;
