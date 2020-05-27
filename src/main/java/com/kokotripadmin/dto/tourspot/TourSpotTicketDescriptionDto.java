package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionImage;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketDescriptionImage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TourSpotTicketDescriptionDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private int order;
    private boolean popup;
    private Integer tourSpotId;
    private String tourSpotName;
    private Integer tourSpotTicketId;
    private String tourSpotTicketName;

    private List<TourSpotTicketDescriptionInfoDto> tourSpotTicketDescriptionInfoDtoList = new ArrayList<>();
    private List<TourSpotTicketDescriptionImageDto> tourSpotTicketDescriptionImageDtoList = new ArrayList<>();

    public TourSpotTicketDescriptionDto() {
    }

    public TourSpotTicketDescriptionDto(Integer id, String name, boolean enabled, String description, int order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}
