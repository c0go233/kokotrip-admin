package com.kokotripadmin.service.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kokotripadmin.exception.amazon_s3_bucket.FileIsNotImageException;
import com.kokotripadmin.exception.amazon_s3_bucket.ImageDuplicateException;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private Convert convert;

    private final String KOKOTRIP_BUCKET = "kokotrip";

    public String uploadImage(String directory, String fileName, MultipartFile multipartFile)
    throws IOException, ImageDuplicateException, FileIsNotImageException {
        //TODO: check if multipartfile is image

        if (amazonS3.doesObjectExist(KOKOTRIP_BUCKET, directory + "/" + fileName))
            throw new ImageDuplicateException(fileName);

        if (!multipartFile.getContentType().startsWith("image/"))
            throw new FileIsNotImageException();

        File file = convert.multiPartToFile(multipartFile, fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(KOKOTRIP_BUCKET, directory + "/" + fileName, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
        return convert.toAmazonS3EndPoint(KOKOTRIP_BUCKET, amazonS3.getRegionName(), directory, fileName);
    }

    public String deleteImage(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(KOKOTRIP_BUCKET, fileName);
        amazonS3.deleteObject(deleteObjectRequest);
        return fileName;
    }
}
