package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDescribableInfoDto extends BaseInfoDto {

    protected String description;
    protected String tagName;
}
