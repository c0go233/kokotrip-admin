package com.kokotripadmin.viewmodel.tag;

import com.kokotripadmin.viewmodel.common.BaseViewModel;
import com.kokotripadmin.viewmodel.common.GenericInfoVm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class TagVm extends BaseViewModel {

    @NotBlank(message = "하위분류명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "하위분류명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;
    private String repImagePath;
    private String themeName;

    @NotNull(message = "상위분류를 선택해주세요")
    private Integer themeId;

    private List<TagInfoVm> tagInfoVmList;

    public void setMetaData(Integer themeId, String themeName, boolean enabled) {
        this.themeId = themeId;
        this.themeName = themeName;
        this.enabled = enabled;
    }
}
