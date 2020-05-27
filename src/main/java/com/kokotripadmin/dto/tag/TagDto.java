package com.kokotripadmin.dto.tag;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kokotripadmin.dto.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class TagDto extends BaseDto {

    private String name;
    private boolean enabled;

    private String repImagePath;
    private String themeName;
    private Integer themeId;

    private List<TagInfoDto> tagInfoDtoList =  new ArrayList<>();

    public TagDto() {
    }

    public TagDto(Integer id, String name, boolean enabled) {
        super(id);
        this.name = name;
        this.enabled = enabled;
    }
}
