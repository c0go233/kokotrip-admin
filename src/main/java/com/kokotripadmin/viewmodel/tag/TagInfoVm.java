package com.kokotripadmin.viewmodel.tag;

import com.kokotripadmin.viewmodel.common.BaseInfoViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TagInfoVm extends BaseInfoViewModel {

    private Integer tagId;

    @NotBlank(message = "하위분류명을 입력해주세요.")
    @Size(min = 2, max = 50, message = "하위분류명은 최소 {min}글자 이상 {max}글자 이하로 입렵해주세요")
    private String name;

    //dummy for infotable
    private String description;
}
