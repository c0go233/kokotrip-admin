package com.kokotripadmin.dto.common;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThemeRelDto {

    private String themeName;
    private int numOfAllTag;

    private List<ThemeTagRelDto> themeTagRelDtoList;
}
