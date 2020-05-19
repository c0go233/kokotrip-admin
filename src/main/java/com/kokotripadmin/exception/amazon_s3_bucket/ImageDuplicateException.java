package com.kokotripadmin.exception.amazon_s3_bucket;

import net.bytebuddy.implementation.bind.annotation.Super;

public class ImageDuplicateException extends Exception {

    public ImageDuplicateException(String fileName) {
        super(fileName + " 은(는) 이미 추가한 이미지 입니다.");
    }
}
