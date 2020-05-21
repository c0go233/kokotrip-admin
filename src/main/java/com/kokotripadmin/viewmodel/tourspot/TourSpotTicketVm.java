package com.kokotripadmin.viewmodel.tourspot;

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
public class TourSpotTicketVm extends BaseViewModel {

    @NotBlank(message = "여행지티켓명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "여행지티켓명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "여행지티켓요약설명을 입력해주세요.")
    @Size(min = 10, max = 300, message = "여행지티켓요약설명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String description;

    @NotNull(message = "여행지티켓 순서를 입력해주세요")
    private Integer order;

    private String repImagePath;
    private Integer tourSpotId;
    private String tourSpotName;

    @TicketPriceConstraint
    private List<TicketPriceVm> ticketPriceVmList;

    private List<TourSpotTicketInfoVm> tourSpotTicketInfoVmList;
    private List<TourSpotTicketDescriptionVm> tourSpotTicketDescriptionVmList;

    public TourSpotTicketVm() {
    }

    public TourSpotTicketVm(boolean enabled, String tourSpotName, Integer tourSpotId) {
        this.enabled = enabled;
        this.tourSpotName = tourSpotName;
        this.tourSpotId = tourSpotId;
    }
}
