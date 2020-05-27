package com.kokotripadmin.viewmodel.tourspot;

import com.kokotripadmin.viewmodel.common.BaseImageVm;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class TourSpotDescriptionVm extends BaseViewModel {

    @NotBlank(message = "여행지설명 머리말을 입력해주세요.")
    @Size(min = 2, max = 50, message = "여행지설명 머리말은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "여행지설명 본문을 입력해주세요.")
    @Size(min = 10, max = 300, message = "여행지설명 본문은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    private Integer order;
    private boolean popup;
    private Integer tourSpotId;
    private String  tourSpotName;


    private List<TourSpotDescriptionInfoVm> tourSpotDescriptionInfoVmList;
    private List<BaseImageVm>                   baseImageVmList;

    public TourSpotDescriptionVm() {
    }

    public TourSpotDescriptionVm(Integer tourSpotId, String tourSpotName, boolean enabled) {
        this.tourSpotId = tourSpotId;
        this.tourSpotName = tourSpotName;
        this.enabled = enabled;
    }
}
