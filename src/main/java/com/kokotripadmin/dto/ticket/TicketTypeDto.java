package com.kokotripadmin.dto.ticket;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class TicketTypeDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String repImagePath;
    private String repImageFileType;

    private List<TicketTypeInfoDto> ticketTypeInfoDtoList = new ArrayList<>();
}
