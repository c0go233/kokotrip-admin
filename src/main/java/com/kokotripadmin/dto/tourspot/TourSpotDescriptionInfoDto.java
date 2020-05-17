package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TourSpotDescriptionInfoDto extends BaseDescribableInfoDto {

    private Integer tourSpotInfoId;
    private Integer tourSpotDescriptionId;
    private Integer order;

}
