package com.kokotripadmin.controller;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
@RequestMapping("/upload")
public class BucketController {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private Convert convert;


    private final String KOKOTRIP_BUCKET = "kokotrip";

    @PostMapping(value = "/image", consumes = {"multipart/form-data"}, produces = "application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity<String> uploadImageToS3Bucket(@Valid @RequestParam("image") MultipartFile multipartFile,
                                                        @Valid @RequestParam("directory") String directory,
                                                        @Valid @RequestParam("fileName") String fileName) {


        try {
//            File file = convert.multiPartToFile(multipartFile, fileName);
//            PutObjectRequest putObjectRequest = new PutObjectRequest(KOKOTRIP_BUCKET, directory + "/" + fileName, file);
//            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
//            amazonS3.putObject(putObjectRequest);
//            file.delete();
//            String endPoint = convert.toAmazonS3EndPoint(KOKOTRIP_BUCKET, amazonS3.getRegionName(), directory, fileName);
//            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson(endPoint));
            return ResponseEntity.status(HttpStatus.OK).body(convert.resultToJson("test path"));
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
        }
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.exceptionToJson(e.getMessage()));
//        }
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
    }

}
