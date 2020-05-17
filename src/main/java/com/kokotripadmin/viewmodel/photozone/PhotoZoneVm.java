package com.kokotripadmin.viewmodel.photozone;

import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PhotoZoneVm extends BaseViewModel {

    @NotBlank(message = "포토존명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "포토존명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "포토존요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "포토존요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @Digits(integer = 6, fraction = 12, message = "위도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double latitude;

    @Digits(integer = 6, fraction = 12, message = "경도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double longitude;

    private int order;
    private String repImagePath;
    private String repImageFileType;
    private Integer parentTourSpotId;
    private String parentTourSpotName;
    private Integer tourSpotId;
    private String tourSpotName;
    private Integer activityId;
    private String activityName;

    private List<PhotoZoneInfoVm> photoZoneInfoVmList = new ArrayList<>();

    public PhotoZoneVm() {
    }

    public PhotoZoneVm(boolean enabled, Integer parentTourSpotId) {
        this.enabled = enabled;
        this.parentTourSpotId = parentTourSpotId;
    }
}
