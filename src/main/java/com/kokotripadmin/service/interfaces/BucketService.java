package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.entity.common.BaseImageEntity;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BucketService {
    String uploadImage(String bucketKey, String fileName, MultipartFile multipartFile)
    throws IOException, ImageDuplicateException, FileIsNotImageException;
    String deleteImage(String bucketKey);
    String getEndPoint(String bucketKey);
    void deleteImages(List<? extends BaseImageEntity> baseImageEntities);
}
