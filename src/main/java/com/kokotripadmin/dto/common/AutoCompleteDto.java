package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutoCompleteDto {

    private Integer id;
    private String name;

    public AutoCompleteDto() {
    }

    public AutoCompleteDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
