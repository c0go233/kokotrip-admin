package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ActivityTicketDescriptionDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private int order;
    private boolean popup;
    private Integer tourSpotId;
    private String tourSpotName;
    private Integer activityId;
    private String activityName;
    private Integer activityTicketId;
    private String activityTicketName;

    private List<ActivityTicketDescriptionInfoDto> activityTicketDescriptionInfoDtoList = new ArrayList<>();
    private List<ActivityTicketDescriptionImageDto> activityTicketDescriptionImageDtoList = new ArrayList<>();

    public ActivityTicketDescriptionDto() {
    }

    public ActivityTicketDescriptionDto(Integer id, String name, boolean enabled, String description, int order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}
