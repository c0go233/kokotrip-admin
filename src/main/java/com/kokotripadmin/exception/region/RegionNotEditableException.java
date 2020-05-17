package com.kokotripadmin.exception.region;

public class RegionNotEditableException extends Exception {
    public RegionNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 유명동네 정보를 직접편집하세요.");
    }
}
