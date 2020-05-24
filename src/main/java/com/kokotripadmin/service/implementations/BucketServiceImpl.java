package com.kokotripadmin.service.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.internal.DeleteObjectsResponse;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.transfer.*;


import com.kokotripadmin.entity.common.BaseImageEntity;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private Convert convert;

    private final String KOKOTRIP_BUCKET = "kokotrip";

    @Override
    public String uploadImage(String bucketKey, String fileName, MultipartFile multipartFile)
    throws IOException, ImageDuplicateException, FileIsNotImageException {

        if (amazonS3.doesObjectExist(KOKOTRIP_BUCKET, bucketKey))
            throw new ImageDuplicateException(bucketKey);

        if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/"))
            throw new FileIsNotImageException();

        File file = convert.multiPartToFile(multipartFile, fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(KOKOTRIP_BUCKET, bucketKey, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);

        return amazonS3.getUrl(KOKOTRIP_BUCKET, bucketKey).toString();
    }


    @Override
    public void deleteImages(List<? extends BaseImageEntity> baseImageEntities) {
        List<KeyVersion> keyVersionList = getKeyVersionList(baseImageEntities);
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(KOKOTRIP_BUCKET).withKeys(keyVersionList)
                                                                                             .withQuiet(false);
        DeleteObjectsResult deleteObjectsResult = amazonS3.deleteObjects(deleteObjectsRequest);
    }

    private List<KeyVersion> getKeyVersionList(List<? extends BaseImageEntity> baseImageEntities) {
        List<KeyVersion> keyVersionList = new ArrayList<>();
        for (BaseImageEntity baseImageEntity : baseImageEntities) {
            keyVersionList.add(new KeyVersion(baseImageEntity.getBucketKey()));
        }
        return keyVersionList;
    }

    public String getEndPoint(String bucketKey) {
        return amazonS3.getUrl(KOKOTRIP_BUCKET, bucketKey).toString();
    }

    @Override
    public String deleteImage(String bucketKey) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(KOKOTRIP_BUCKET, bucketKey);
        amazonS3.deleteObject(deleteObjectRequest);
        return bucketKey;
    }
}


//upload file list


//    TransferManager tm = TransferManagerBuilder.standard()
//                                               .withS3Client(amazonS3).build();


//        TransferManager tm = TransferManagerBuilder.standard()
//                                                   .withS3Client(amazonS3)
//                                                   .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
//                                                   .build();

//        ObjectMetadataProvider metadataProvider = new ObjectMetadataProvider() {
//            void provideObjectMetadata(File file, ObjectMetadata metadata) {
//                // If this file is a JPEG, then parse some additional info
//                // from the EXIF metadata to store in the object metadata
//                if (isJPEG(file)) {
//                    metadata.addUserMetadata("original-image-date",
//                                             parseExifImageDate(file));
//                }
//            }
//        }


//    ObjectCannedAclProvider cannedAclProvider = new ObjectCannedAclProvider() {
//
//        public CannedAccessControlList provideObjectCannedAcl(File file) {
//            return CannedAccessControlList.PublicRead;
//        }
//    };
//
//    List<File> fileList = new ArrayList<>();
//    File file = convert.multiPartToFile(multipartFile, "./" + fileName);
//        fileList.add(file);
//                File file2 = convert.multiPartToFile(multipartFile, "./" + "mutitestpic2.jpg");
//                fileList.add(file2);
//
//
//                MultipleFileUpload upload = tm.uploadFileList(KOKOTRIP_BUCKET,
//                "city/image/서울",
//                new File("."),
//                fileList,
//                null,
//                null,
//                cannedAclProvider);

//        tm.uploadFileList()
