package com.kokotripadmin.exception.region;

public class RegionInfoNotEditableException extends Exception {

    public RegionInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 유명지역정보를 직접편집하세요.");
    }
}
