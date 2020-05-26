package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class ActivityImageDto extends BaseImageDto {

    private Integer activityId;
    private boolean repImage;

    public ActivityImageDto() {
    }

    public ActivityImageDto(String name, String fileType, Integer order, boolean repImage,
                            Integer activityId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.activityId = activityId;
        this.repImage = repImage;
    }
}
