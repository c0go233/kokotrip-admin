package com.kokotripadmin.viewmodel.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseImageVm {
    private Integer id;
    private String name;
    private String fileType;
    private String path;
    private boolean repImage;
    private Integer order;
}
