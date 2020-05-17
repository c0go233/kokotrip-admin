package com.kokotripadmin.viewmodel.region;


import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import com.kokotripadmin.viewmodel.common.ThemeRelVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class RegionVm extends BaseViewModel {

    @NotBlank(message = "유명지역명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "유명지역명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "유명지역요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "유명지역요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @Digits(integer = 6, fraction = 12, message = "위도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double latitude;

    @Digits(integer = 6, fraction = 12, message = "경도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double longitude;

    private String repImagePath;
    private String repImageFileType;
    private Integer stateId;
    private String stateName;

    @NotNull(message = "도시를 선택해주세요")
    private Integer cityId;

    private String cityName;

    private List<ThemeRelVm> themeRelVmList;
    private List<RegionInfoVm> regionInfoVmList;

    public RegionVm() {
    }

    public RegionVm(String name) {
        this.name = name;
    }

    public RegionVm(boolean enabled) {
        this.enabled = enabled;
    }

    public void setMetaData(String stateName, Integer stateId, String cityName, Integer cityId) {
        this.stateName = stateName;
        this.stateId = stateId;
        this.cityName = cityName;
        this.cityId = cityId;
    }
}
