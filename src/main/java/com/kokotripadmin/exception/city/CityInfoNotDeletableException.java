package com.kokotripadmin.exception.city;

public class CityInfoNotDeletableException extends Exception {

    public CityInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 도시를 삭제하세요.");
    }
}
