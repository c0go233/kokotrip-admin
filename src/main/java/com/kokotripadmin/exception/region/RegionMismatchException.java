package com.kokotripadmin.exception.region;

public class RegionMismatchException extends Exception {
    public RegionMismatchException(String regionName, String cityName) {
        super(regionName + " 은(는) " + cityName + " 에 속하는 지역이 아닙니다.");
    }
}
