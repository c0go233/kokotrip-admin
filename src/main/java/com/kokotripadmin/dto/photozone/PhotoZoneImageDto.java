package com.kokotripadmin.dto.photozone;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class PhotoZoneImageDto extends BaseImageDto {

    private Integer photoZoneId;
    private boolean repImage;

    public PhotoZoneImageDto() {
    }

    public PhotoZoneImageDto(String name, String fileType, Integer order, boolean repImage,
                             Integer photoZoneId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.photoZoneId = photoZoneId;
        this.repImage = repImage;
    }
}
