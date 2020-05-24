package com.kokotripadmin.dto.city;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CityImageDto extends BaseImageDto {

    private Integer cityId;

    public CityImageDto() {
    }

    public CityImageDto(String name, String fileType, Integer order, boolean repImage,
                        Integer cityId, MultipartFile multipartFile) {
        super(name, fileType, order, repImage, multipartFile);
        this.cityId = cityId;
    }
}
