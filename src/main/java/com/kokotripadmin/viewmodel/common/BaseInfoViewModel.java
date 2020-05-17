package com.kokotripadmin.viewmodel.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseInfoViewModel extends BaseViewModel {

    protected Integer supportLanguageId;
    protected String supportLanguageName;
    protected String tagName;
}
