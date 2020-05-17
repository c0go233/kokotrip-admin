package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TourSpotTicketInfoDto extends BaseDescribableInfoDto {

    private Integer tourSpotTicketId;
    private Integer order;

}
