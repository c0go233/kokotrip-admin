package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseImageDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ActivityTicketImageDto extends BaseImageDto {

    private Integer activityTicketId;
    private boolean repImage;

    public ActivityTicketImageDto() {
    }

    public ActivityTicketImageDto(String name, String fileType, Integer order, boolean repImage,
                        Integer activityTicketId, MultipartFile multipartFile) {
        super(name, fileType, order, multipartFile);
        this.activityTicketId = activityTicketId;
        this.repImage = repImage;
    }
}
