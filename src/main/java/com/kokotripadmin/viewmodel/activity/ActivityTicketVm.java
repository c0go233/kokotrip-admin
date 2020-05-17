package com.kokotripadmin.viewmodel.activity;

import com.kokotripadmin.validator.TicketPriceConstraint;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.ticket.TicketPriceVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class ActivityTicketVm extends BaseViewModel {

    @NotBlank(message = "액티비티 티켓명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "액티비티 티켓명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "액티비티 티켓요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "액티비티 티켓요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @NotNull(message = "액티비티 티켓순서를 입력해주세요")
    private Integer order;

    private String repImagePath;
    private String repImageFileType;
    private Integer tourSpotId;
    private String tourSpotName;
    private Integer activityId;
    private String activityName;

    @TicketPriceConstraint
    private List<TicketPriceVm> ticketPriceVmList;

    private List<ActivityTicketInfoVm> activityTicketInfoVmList;
    private List<ActivityTicketDescriptionVm> activityTicketDescriptionVmList;

    public ActivityTicketVm() {
    }

    public ActivityTicketVm(boolean enabled, Integer tourSpotId, String tourSpotName, Integer activityId,
                            String activityName) {
        this.enabled = enabled;
        this.tourSpotId = tourSpotId;
        this.tourSpotName = tourSpotName;
        this.activityId = activityId;
        this.activityName = activityName;
    }
}
