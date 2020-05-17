package com.kokotripadmin.viewmodel.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericInfoVm extends BaseViewModel {

    private String name;
    private boolean enabled;
    private String description;
    private Integer supportLanguageId;
    private String  supportLanguageName;
    private Integer ownerId;
    private String tagName;
    private boolean popup;

}
