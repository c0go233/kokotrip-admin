package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TourSpotTicketDescriptionImageDto extends BaseImageDto {

    private Integer tourSpotTicketDescriptionId;

    public TourSpotTicketDescriptionImageDto() {
    }

    public TourSpotTicketDescriptionImageDto(String name, String fileType, Integer order,
                                             Integer tourSpotTicketDescriptionId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.tourSpotTicketDescriptionId = tourSpotTicketDescriptionId;
    }
}
