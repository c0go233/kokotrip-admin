package com.kokotripadmin.viewmodel.theme;

import com.kokotripadmin.viewmodel.common.BaseInfoViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ThemeInfoVm extends BaseInfoViewModel {

    private Integer themeId;

    @NotBlank(message = "상위분류명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "상위분류명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    //dummy for infotable
    private String description;
}
