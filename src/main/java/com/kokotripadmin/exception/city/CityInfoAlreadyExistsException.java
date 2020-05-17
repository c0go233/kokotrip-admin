package com.kokotripadmin.exception.city;

public class CityInfoAlreadyExistsException extends Exception {

    public CityInfoAlreadyExistsException(String cityName, String supportLanguageName) {
        super("도시: " + cityName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}
