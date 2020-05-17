package com.kokotripadmin.dao.custom;

import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;

import java.util.List;

public interface CustomizedSupportLanguageDao {
    SupportLanguage findDefaultSupportLanguage() throws SupportLanguageNotFoundException;
    void customizedMethod();
    List<SupportLanguage> findAllByEnabled(boolean enabled);
}
