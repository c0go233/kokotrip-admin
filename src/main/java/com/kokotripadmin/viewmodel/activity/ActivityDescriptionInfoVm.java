package com.kokotripadmin.viewmodel.activity;

import com.kokotripadmin.viewmodel.common.BaseInfoViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ActivityDescriptionInfoVm extends BaseInfoViewModel {

    private Integer activityDescriptionId;

    @NotBlank(message = "액티비티설명 머리말을 입력해주세요.")
    @Size(min = 2, max = 50, message = "액티비티설명 머리말은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    @NotBlank(message = "액티비티설명 본문을 입력해주세요.")
    @Size(min = 10, max = 300, message = "액티비티설명 본문은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

}