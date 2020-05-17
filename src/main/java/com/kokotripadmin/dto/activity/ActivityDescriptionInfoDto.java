package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityDescriptionInfoDto extends BaseDescribableInfoDto {

    private Integer activityInfoId;
    private Integer activityDescriptionId;
    private Integer order;
}
