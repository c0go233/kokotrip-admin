package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivityTicketDescriptionInfoDto extends BaseDescribableInfoDto {

    private Integer activityTicketInfoId;
    private Integer activityTicketDescriptionId;
    private Integer order;

}
