package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class ActivityDescriptionImageDto extends BaseImageDto {

    private Integer activityDescriptionId;

    public ActivityDescriptionImageDto() {
    }

    public ActivityDescriptionImageDto(Integer id, String name, String url, Integer order) {
        super(id, name, url, order);
    }

    public ActivityDescriptionImageDto(String name, String fileType, Integer order,
                                       Integer activityDescriptionId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.activityDescriptionId = activityDescriptionId;
    }
}
