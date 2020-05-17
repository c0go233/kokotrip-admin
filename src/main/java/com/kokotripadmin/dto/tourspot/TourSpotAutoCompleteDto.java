package com.kokotripadmin.dto.tourspot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourSpotAutoCompleteDto {

    private Integer id;
    private double latitude;
    private double longitude;

    public TourSpotAutoCompleteDto(Integer id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
