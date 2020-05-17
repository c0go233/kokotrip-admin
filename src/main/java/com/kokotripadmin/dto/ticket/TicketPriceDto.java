package com.kokotripadmin.dto.ticket;

import com.kokotripadmin.dto.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TicketPriceDto extends BaseDto {

    private Double  price;
    private boolean repPrice;
    private Integer ticketTypeId;
    private String  ticketTypeName;

}
