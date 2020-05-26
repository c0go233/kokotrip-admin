package com.kokotripadmin.viewmodel.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BaseImageVm extends BaseViewModel {
    private boolean       enabled;
    private String        name;
    private String        fileType;
    private String        url;
    private boolean       repImage;
    private Integer       order;
    private MultipartFile multipartFile;
}
