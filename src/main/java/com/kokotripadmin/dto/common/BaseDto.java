package com.kokotripadmin.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


@Getter
@Setter
public class BaseDto {

    protected Integer id;
    protected String createdAt;

//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected String updatedAt;

    public BaseDto() {
    }

    public BaseDto(Integer id) {
        this.id = id;
    }

    public BaseDto(Integer id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
