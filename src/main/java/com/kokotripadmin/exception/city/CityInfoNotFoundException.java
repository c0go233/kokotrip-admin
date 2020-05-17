package com.kokotripadmin.exception.city;

public class CityInfoNotFoundException extends Exception {

    public CityInfoNotFoundException() {
        super("도시 번역정보를 찾을수 없습니다.");
    }
}
