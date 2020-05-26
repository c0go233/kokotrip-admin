package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BaseImageDto extends BaseDto {

    private boolean enabled;
    private String  name;
    private String  fileType;
    private String  url;
    private Integer order;
    private MultipartFile multipartFile;

    public BaseImageDto() {
    }

    public BaseImageDto(String name, String fileType, Integer order, MultipartFile multipartFile) {
        this.enabled = true;
        this.name = name;
        this.fileType = fileType;
        this.multipartFile = multipartFile;
        this.order = order;
    }

    public BaseImageDto(Integer id, String name, String url, Integer order) {
        super(id);
        this.name = name;
        this.url = url;
        this.order = order;
    }
}
