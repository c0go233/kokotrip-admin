package com.kokotripadmin.exception.city;

public class CityInfoNotEditableException extends Exception {

    public CityInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 도시정보를 직접편집하세요.");
    }
}
