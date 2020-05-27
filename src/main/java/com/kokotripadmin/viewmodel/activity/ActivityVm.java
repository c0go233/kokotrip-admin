package com.kokotripadmin.viewmodel.activity;

import com.kokotripadmin.viewmodel.common.BaseImageVm;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ActivityVm extends BaseViewModel {

    @NotBlank(message = "액티비티명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "액티비티명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "액티비티요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "액티비티요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @NotNull(message = "하위분류를 선택해주세요")
    private Integer tagId;


    private int order;
    private double latitude;
    private double longitude;
    private double popularScore;
    private double averageRate;
    private int numberOfRate;
    private int numberOfWishListSaved;
    private String repImagePath;
    private String tourSpotName;
    private Integer tourSpotId;
    private String tagName;



    private List<ActivityInfoVm> activityInfoVmList;
    private List<ActivityDescriptionVm> activityDescriptionVmList;
    private List<ActivityTicketVm> activityTicketVmList;
    private List<BaseImageVm> baseImageVmList;

    public ActivityVm() {
    }

    public ActivityVm(boolean enabled) {
        this.enabled = enabled;
    }


}
