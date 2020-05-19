package com.kokotripadmin.exception.amazon_s3_bucket;

public class FileIsNotImageException extends Exception {

    public FileIsNotImageException() {
        super("이미지만 업로드할수있습니다.");
    }
}
