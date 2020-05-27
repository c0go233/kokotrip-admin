package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ActivityDescriptionDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private int order;
    private boolean popup;
    private Integer activityId;
    private String activityName;
    private Integer tourSpotId;
    private String tourSpotName;

    private List<ActivityDescriptionInfoDto> activityDescriptionInfoDtoList = new ArrayList<>();
    private List<ActivityDescriptionImageDto> activityDescriptionImageDtoList = new ArrayList<>();

    public ActivityDescriptionDto(Integer id, String name, boolean enabled, String description, int order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}
