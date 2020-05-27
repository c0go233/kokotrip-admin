package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class TourSpotImageDto extends BaseImageDto {

    private Integer tourSpotId;
    private boolean repImage;

    public TourSpotImageDto() {
    }

    public TourSpotImageDto(Integer id, String name, String url, Integer order, boolean repImage) {
        super(id, name, url, order);
        this.repImage = repImage;
    }

    public TourSpotImageDto(String name, String fileType, Integer order, boolean repImage,
                            Integer tourSpotId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.repImage = repImage;
        this.tourSpotId = tourSpotId;
    }
}
