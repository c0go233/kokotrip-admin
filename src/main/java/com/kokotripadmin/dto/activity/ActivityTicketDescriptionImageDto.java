package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class ActivityTicketDescriptionImageDto extends BaseImageDto {

    private Integer activityTicketDescriptionId;

    public ActivityTicketDescriptionImageDto() {
    }

    public ActivityTicketDescriptionImageDto(String name, String fileType, Integer order,
                        Integer activityTicketDescriptionId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.activityTicketDescriptionId = activityTicketDescriptionId;
    }
}
