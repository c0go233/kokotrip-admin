package com.kokotripadmin.viewmodel.state;

import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.viewmodel.city.CityVm;
import com.kokotripadmin.viewmodel.common.BaseViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Getter
@Setter
public class StateVm extends BaseViewModel {


    @NotBlank(message = "시,도명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "시,도명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    private List<CityVm> cityVmList = new ArrayList<>();

    public StateVm() {
    }

    public StateVm(boolean enabled) {
        this.enabled = enabled;
    }
}
