package com.kokotripadmin.exception.region;

public class RegionNotFoundException extends Exception {

    public RegionNotFoundException() {
        super("유명동네를 찾을수가 없습니다.");
    }

    public RegionNotFoundException(Integer famousRegionId) {
        super("유명동네 (id=" + famousRegionId + ") 가 없는디???????");
    }
}
