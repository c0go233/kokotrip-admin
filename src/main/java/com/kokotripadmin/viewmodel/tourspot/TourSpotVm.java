package com.kokotripadmin.viewmodel.tourspot;

import com.kokotripadmin.validator.TradingHourConstraint;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.TradingHourVm;
import com.kokotripadmin.viewmodel.activity.ActivityVm;
import com.kokotripadmin.viewmodel.photozone.PhotoZoneVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@TradingHourConstraint
public class TourSpotVm extends BaseViewModel {


    @NotBlank(message = "여행지명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "여행지명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "여행지요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "여행지요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @Digits(integer = 6, fraction = 12, message = "위도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double latitude;

    @Digits(integer = 6, fraction = 12, message = "경도는 자연수 {integer}자리 이하 소수 {fraction}자리 이하로 입력해주세요")
    private double longitude;

    @NotNull(message = "도시를 선택해주세요")
    private Integer cityId;

    private String  cityName;
    private Integer regionId;
    private String  regionName;

    private double  popularScore;
    private boolean alwaysOpen;
    private String  websiteLink;
    private String  contact;
    private String  address;
    private String  repImagePath;

    @NotNull(message = "하위분류를 선택해주세요")
    private Integer tagId;

    private String tagName;

    private double averageRate;
    private int    numberOfRate;
    private int    numberOfWishListSaved;

    private List<TourSpotInfoVm>        tourSpotInfoVmList;
    private List<TradingHourVm>         tradingHourVmList;
    private List<TourSpotDescriptionVm> tourSpotDescriptionVmList;
    private List<TourSpotTicketVm>      tourSpotTicketVmList;
    private List<ActivityVm>            activityVmList;
    private List<PhotoZoneVm>           photoZoneVmList;

    public TourSpotVm() {
    }

    public TourSpotVm(boolean enabled) {
        this.enabled = enabled;
    }
}
