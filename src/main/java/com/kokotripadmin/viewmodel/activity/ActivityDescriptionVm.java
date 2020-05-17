package com.kokotripadmin.viewmodel.activity;

import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ActivityDescriptionVm extends BaseViewModel {


    @NotBlank(message = "액티비티설명 머리말을 입력해주세요.")
    @Size(min = 2, max = 50, message = "액티비티설명 머리말은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "액티비티설명 본문을 입력해주세요.")
    @Size(min = 10, max = 300, message = "액티비티설명 본문은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    private Integer order;
    private boolean popup;
    private Integer activityId;
    private String activityName;
    private Integer tourSpotId;
    private String tourSpotName;

    private List<ActivityDescriptionInfoVm> activityDescriptionInfoVmList;


    public ActivityDescriptionVm() {
    }


    public ActivityDescriptionVm(boolean enabled,
                                 Integer tourSpotId,
                                 String tourSpotName,
                                 Integer activityId,
                                 String activityName) {
        this.enabled = enabled;
        this.tourSpotId = tourSpotId;
        this.tourSpotName = tourSpotName;
        this.activityId = activityId;
        this.activityName = activityName;
    }
}
