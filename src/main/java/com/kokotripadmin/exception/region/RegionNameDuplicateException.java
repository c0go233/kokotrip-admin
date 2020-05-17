package com.kokotripadmin.exception.region;

public class RegionNameDuplicateException extends Exception {

    public RegionNameDuplicateException(String regionName) {
        super(regionName +  " 은(는) 이미 추가한 동네입니다.");
    }
}
