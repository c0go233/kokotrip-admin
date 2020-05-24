package com.kokotripadmin.exception.image;

public class RepImageNotDeletableException extends Exception {
    public RepImageNotDeletableException() {
        super("대표이미지는 삭제할수없습니다");
    }
}
