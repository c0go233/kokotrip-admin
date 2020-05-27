package com.kokotripadmin.dto.tourspot;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class TourSpotTicketImageDto extends BaseImageDto {

    private Integer tourSpotTicketId;
    private boolean repImage;

    public TourSpotTicketImageDto() {
    }

    public TourSpotTicketImageDto(Integer id, String name, String url, Integer order, boolean repImage) {
        super(id, name, url, order);
        this.repImage = repImage;
    }

    public TourSpotTicketImageDto(String name, String fileType, Integer order, boolean repImage,
                            Integer tourSpotTicketId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.tourSpotTicketId = tourSpotTicketId;
        this.repImage = repImage;
    }
}
