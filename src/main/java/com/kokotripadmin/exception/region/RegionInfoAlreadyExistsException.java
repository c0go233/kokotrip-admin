package com.kokotripadmin.exception.region;

public class RegionInfoAlreadyExistsException extends Exception {


    public RegionInfoAlreadyExistsException(String regionName, String supportLanguageName) {
        super("동네: " + regionName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }

}
