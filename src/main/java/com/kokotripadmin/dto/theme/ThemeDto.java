package com.kokotripadmin.dto.theme;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.tag.TagDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ThemeDto extends BaseDto {

    private String name;

    private boolean enabled;

    private String repImagePath;
    private String repImageFileType;

    private List<TagDto>       tagDtoList = new ArrayList<>();
    private List<ThemeInfoDto> themeInfoDtoList = new ArrayList<>();

    public ThemeDto() {
    }

    public ThemeDto(Integer id, String name, boolean enabled) {
        super(id);
        this.name = name;
        this.enabled = enabled;
    }
}
