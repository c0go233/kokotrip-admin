package com.kokotripadmin.dto.common;


import com.kokotripadmin.dto.common.BaseDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SupportLanguageDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String displayName;
    private int order;
}
