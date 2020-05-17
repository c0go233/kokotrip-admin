package com.kokotripadmin.viewmodel.ticket;

import com.kokotripadmin.viewmodel.common.BaseViewModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketPriceVm extends BaseViewModel {

    private Double  price;
    private boolean repPrice;
    private Integer ticketTypeId;
    private String  ticketTypeName;
}
