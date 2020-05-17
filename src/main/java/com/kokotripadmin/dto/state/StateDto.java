package com.kokotripadmin.dto.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class StateDto extends BaseDto {

    private String name;
    private boolean enabled;

    private List<CityDto> cityDtoList = new ArrayList<>();

    public StateDto() {
    }

    public StateDto(Integer id, String name, boolean enabled) {
        super(id);
        this.name = name;
        this.enabled = enabled;
    }
}
