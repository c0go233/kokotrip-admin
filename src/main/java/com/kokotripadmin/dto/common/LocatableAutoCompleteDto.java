package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocatableAutoCompleteDto extends AutoCompleteDto {


    private double latitude;
    private double longitude;

    public LocatableAutoCompleteDto() {
    }

    public LocatableAutoCompleteDto(Integer id, String name, double latitude, double longitude) {
        super(id, name);
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
