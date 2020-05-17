package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.ticket.TicketPriceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ActivityTicketDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private Integer order;
    private String repImagePath;
    private String repImageFileType;
    private Integer tourSpotId;
    private String tourSpotName;
    private Integer activityId;
    private String activityName;

    private List<ActivityTicketInfoDto>        activityTicketInfoDtoList        = new ArrayList<>();
    private List<TicketPriceDto>               ticketPriceDtoList               = new ArrayList<>();
    private List<ActivityTicketDescriptionDto> activityTicketDescriptionDtoList = new ArrayList<>();

    public ActivityTicketDto() {
    }

    public ActivityTicketDto(Integer id, String name, boolean enabled, String description, Integer order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}
