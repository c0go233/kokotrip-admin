package com.kokotripadmin.exception.region;

public class RegionInfoNotFoundException extends Exception {

    public RegionInfoNotFoundException() {
        super("유명동네 번역정보를 찾을수 없습니다.");
    }
}
