package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.ticket.TicketPriceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class TourSpotTicketDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private Integer order;
    private String repImagePath;
    private String repImageFileType;
    private Integer tourSpotId;
    private String tourSpotName;

    private List<TourSpotTicketInfoDto> tourSpotTicketInfoDtoList = new ArrayList<>();
    private List<TicketPriceDto> ticketPriceDtoList = new ArrayList<>();
    private List<TourSpotTicketDescriptionDto> tourSpotTicketDescriptionDtoList = new ArrayList<>();

    public TourSpotTicketDto() {
    }

    public TourSpotTicketDto(Integer id, String name, boolean enabled, String description, Integer order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}
