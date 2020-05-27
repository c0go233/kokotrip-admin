package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TourSpotDescriptionImageDto extends BaseImageDto {

    private Integer tourSpotDescriptionId;

    public TourSpotDescriptionImageDto() {
    }

    public TourSpotDescriptionImageDto(Integer id, String name, String url, Integer order) {
        super(id, name, url, order);
    }

    public TourSpotDescriptionImageDto(String name,
                                       String fileType,
                                       Integer order,
                                       Integer tourSpotDescriptionId,
                                       MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.tourSpotDescriptionId = tourSpotDescriptionId;
    }
}
