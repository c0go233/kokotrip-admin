package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourSpotTicketDescriptionInfoDto extends BaseDescribableInfoDto {

    private Integer tourSpotTicketInfoId;
    private Integer tourSpotTicketDescriptionId;
    private Integer order;

}
