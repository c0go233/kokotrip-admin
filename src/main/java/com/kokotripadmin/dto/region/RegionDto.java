package com.kokotripadmin.dto.region;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.common.ThemeRelDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class RegionDto extends BaseDto {

    private String name;

    private boolean enabled;

    private String description;
    private double latitude;
    private double longitude;
    private String repImagePath;
    private String repImageFileType;
    private Integer stateId;
    private String stateName;
    private Integer cityId;
    private String cityName;

    private List<RegionInfoDto> regionInfoDtoList =  new ArrayList<>();
    private List<ThemeRelDto> themeRelDtoList = new ArrayList<>();
    private List<RegionImageDto> regionImageDtoList = new ArrayList<>();

    public RegionDto() {
    }

    public RegionDto(Integer id, String name, boolean enabled, String description) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
    }
}
