package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;

public interface SupportLanguageEntityService {

    SupportLanguage findEntityById(Integer id) throws SupportLanguageNotFoundException;
}
