package com.kokotripadmin.exception.image;

public class ImageDuplicateException extends Exception {

    public ImageDuplicateException(String fileName) {
        super(fileName + " 은(는) 이미 추가한 이미지 입니다.");
    }
}
