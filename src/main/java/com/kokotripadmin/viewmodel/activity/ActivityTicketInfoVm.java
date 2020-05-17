package com.kokotripadmin.viewmodel.activity;

import com.kokotripadmin.viewmodel.common.BaseInfoViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class ActivityTicketInfoVm extends BaseInfoViewModel {

    private Integer activityTicketId;

    @NotBlank(message = "액티비티티켓명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "액티비티티켓명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    @NotBlank(message = "액티비티티켓요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "액티비티티켓요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;
}
