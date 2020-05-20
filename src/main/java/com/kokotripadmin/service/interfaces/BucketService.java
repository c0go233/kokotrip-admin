package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.exception.amazon_s3_bucket.FileIsNotImageException;
import com.kokotripadmin.exception.amazon_s3_bucket.ImageDuplicateException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

public interface BucketService {
    String uploadImage(String directory, String fileName, MultipartFile multipartFile)
    throws IOException, ImageDuplicateException, FileIsNotImageException;
    String deleteImage(String fileName);
}
