package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class BaseInfoDto extends BaseDto {

    protected String name;
    protected Integer supportLanguageId;
    protected String supportLanguageName;
}
