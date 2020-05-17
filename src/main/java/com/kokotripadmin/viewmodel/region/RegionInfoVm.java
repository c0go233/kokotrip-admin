package com.kokotripadmin.viewmodel.region;

import com.kokotripadmin.viewmodel.common.BaseInfoViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class RegionInfoVm extends BaseInfoViewModel {

    private Integer regionId;

    @NotBlank(message = "유명지역명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "유명지역명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    @NotBlank(message = "유명지역요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "유명지역요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;
}
