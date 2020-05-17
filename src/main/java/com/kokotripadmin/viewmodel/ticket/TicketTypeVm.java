package com.kokotripadmin.viewmodel.ticket;

import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class TicketTypeVm extends BaseViewModel {

    @NotBlank(message = "티켓타입명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "티켓타입명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;
    private String repImagePath;
    private String repImageFileType;

    private List<TicketTypeInfoVm> ticketTypeInfoVmList;

    public TicketTypeVm() {
    }

    public TicketTypeVm(boolean enabled) {
        this.enabled = enabled;
    }
}

