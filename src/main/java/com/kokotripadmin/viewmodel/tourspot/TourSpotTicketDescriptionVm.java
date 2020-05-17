package com.kokotripadmin.viewmodel.tourspot;

import com.kokotripadmin.viewmodel.common.BaseViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class TourSpotTicketDescriptionVm extends BaseViewModel {

    @NotBlank(message = "여행지티켓설명 머리말을 입력해주세요.")
    @Size(min = 2, max = 50, message = "여행지티켓설명 머리말은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "여행지티켓설명 본문을 입력해주세요.")
    @Size(min = 10, max = 300, message = "여행지티켓설명 본문은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    private Integer order;
    private boolean popup;
    private Integer tourSpotId;
    private String  tourSpotName;
    private Integer tourSpotTicketId;
    private String tourSpotTicketName;

    private List<TourSpotTicketDescriptionInfoVm> tourSpotTicketDescriptionInfoVmList;


    public TourSpotTicketDescriptionVm() {
    }

    public TourSpotTicketDescriptionVm(Integer tourSpotId,
                                       String tourSpotName,
                                       Integer tourSpotTicketId,
                                       String tourSpotTicketName,
                                       boolean enabled) {
        this.enabled = enabled;
        this.tourSpotId = tourSpotId;
        this.tourSpotName = tourSpotName;
        this.tourSpotTicketId = tourSpotTicketId;
        this.tourSpotTicketName = tourSpotTicketName;
    }
}
