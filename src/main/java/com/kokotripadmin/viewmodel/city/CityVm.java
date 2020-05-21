package com.kokotripadmin.viewmodel.city;

import com.kokotripadmin.validator.ImageConstraint;
import com.kokotripadmin.viewmodel.common.BaseImageVm;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import com.kokotripadmin.viewmodel.common.ThemeRelVm;
import com.kokotripadmin.viewmodel.region.RegionVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CityVm extends BaseViewModel {

    @NotBlank(message = "도시명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "도시명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "도시요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "도시요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @Digits(integer = 6, fraction = 12, message = "위도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double latitude;

    @Digits(integer = 6, fraction = 12, message = "경도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double longitude;

    private String  repImagePath;
    private String  stateName;

    @NotNull(message = "시,도를 선택해주세요")
    private Integer stateId;

    private List<CityInfoVm> cityInfoVmList;
    private List<ThemeRelVm> themeRelVmList;
    private List<RegionVm>   regionVmList;

    @ImageConstraint
    private List<BaseImageVm> baseImageVmList;

    public CityVm() {
    }

    public CityVm(String stateName, Integer stateId, boolean enabled) {
        this.stateName = stateName;
        this.stateId = stateId;
        this.enabled = enabled;
    }
}
