package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseImageDto {
    private Integer id;
    private String name;
    private String fileType;
    private String path;
    private boolean repImage;
    private Integer order;
}
