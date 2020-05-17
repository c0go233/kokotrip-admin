package com.kokotripadmin.exception.city;


public class CityNameDuplicateException extends Exception {

    public CityNameDuplicateException(String cityName) {

        super(cityName +  " 은(는) 이미 추가한 도시입니다.");
    }
}
