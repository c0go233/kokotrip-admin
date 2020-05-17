package com.kokotripadmin.viewmodel.theme;

import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import com.kokotripadmin.viewmodel.tag.TagVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class ThemeVm extends BaseViewModel {

    @NotBlank(message = "상위분류명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "상위분류명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;
    private String repImagePath;
    private String repImageFileType;

    private List<ThemeInfoVm> themeInfoVmList;
    private List<TagVm> tagVmList;

    public ThemeVm() {
    }

    public ThemeVm(boolean enabled) {
        this.enabled = enabled;
    }
}
