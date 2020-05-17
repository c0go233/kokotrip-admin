package com.kokotripadmin.viewmodel.tourspot;

import com.kokotripadmin.viewmodel.common.BaseInfoViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class TourSpotInfoVm extends BaseInfoViewModel {

    private Integer tourSpotId;

    @NotBlank(message = "여행지명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "여행지명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    @NotBlank(message = "여행지요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "여행지요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;


}

