package com.kokotripadmin.viewmodel.common;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SupportLanguageVm extends BaseViewModel {

    @NotBlank(message = "언어명을 입력해주세요.")
    @Size(min = 1, max = 50, message = "언어명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    private boolean enabled;

    @NotBlank(message = "언어표시명을 입력해주세요.")
    @Size(min = 1, max = 50, message = "언어표시명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String displayName;

    private int order;

    public SupportLanguageVm() {
    }

    public SupportLanguageVm(boolean enabled) {
        this.enabled = enabled;
    }
}
