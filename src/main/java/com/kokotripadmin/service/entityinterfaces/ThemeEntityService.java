package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.exception.theme.ThemeNotFoundException;

public interface ThemeEntityService {
    Theme findEntityById(Integer themeId) throws ThemeNotFoundException;
}
