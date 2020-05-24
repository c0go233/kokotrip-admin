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
    private boolean repImage;
    private Integer order;
    private MultipartFile multipartFile;

    public BaseImageDto() {
    }

    public BaseImageDto(String name, String fileType, Integer order, boolean repImage, MultipartFile multipartFile) {
        this.enabled = true;
        this.name = name;
        this.fileType = fileType;
        this.multipartFile = multipartFile;
        this.order = order;
        this.repImage = repImage;
    }

    public BaseImageDto(Integer id, String name, String url, boolean repImage) {
        super(id);
        this.name = name;
        this.url = url;
        this.repImage = repImage;
    }
}
