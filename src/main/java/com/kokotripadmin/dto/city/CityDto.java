package com.kokotripadmin.dto.city;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.common.BaseImageDto;
import com.kokotripadmin.dto.common.ThemeRelDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.entity.city.CityImage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class CityDto extends BaseDto {

    private String name;

    private boolean enabled;

    private String description;
    private double latitude;
    private double longitude;
    private String repImagePath;
    private String stateName;
    private Integer stateId;

    private List<CityInfoDto>  cityInfoDtoList = new ArrayList<>();
    private List<ThemeRelDto>  themeRelDtoList = new ArrayList<>();
    private List<RegionDto>    regionDtoList   = new ArrayList<>();
    private List<BaseImageDto> baseImageDtoList   = new ArrayList<>();

    public CityDto() {
    }

    public CityDto(Integer id, String name, boolean enabled, String description) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
    }
}
