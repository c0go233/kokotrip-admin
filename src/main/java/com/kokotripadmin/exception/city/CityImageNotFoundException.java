package com.kokotripadmin.exception.city;

public class CityImageNotFoundException extends Exception {

    public CityImageNotFoundException() {
        super("도시 이미지를 찾기 못했습니다");
    }
}
