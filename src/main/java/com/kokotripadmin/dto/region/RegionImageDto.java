package com.kokotripadmin.dto.region;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegionImageDto extends BaseImageDto {

    private Integer regionId;
    private boolean repImage;

    public RegionImageDto() {
    }

    public RegionImageDto(Integer id, String name, String url, Integer order, boolean repImage) {
        super(id, name, url, order);
        this.repImage = repImage;
    }

    public RegionImageDto(String name, String fileType, Integer order, boolean repImage,
                          Integer regionId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.regionId = regionId;
        this.repImage = repImage;
    }
}
