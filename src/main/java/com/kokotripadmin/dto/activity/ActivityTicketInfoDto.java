package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivityTicketInfoDto extends BaseDescribableInfoDto {

    private Integer activityTicketId;
    private Integer order;

}
