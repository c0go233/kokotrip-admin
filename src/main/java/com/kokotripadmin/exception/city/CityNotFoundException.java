package com.kokotripadmin.exception.city;

public class CityNotFoundException extends Exception {

    public CityNotFoundException() {
        super("도시를 찾을수가 없습니다.");
    }

    public CityNotFoundException(Integer cityId) {
        super("도시(id=" + cityId + ") 가 없는디???????");
    }
}
