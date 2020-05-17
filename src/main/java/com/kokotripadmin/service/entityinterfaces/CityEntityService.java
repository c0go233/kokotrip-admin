package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.city.CityInfo;
import com.kokotripadmin.entity.city.CityThemeRel;
import com.kokotripadmin.entity.city.CityThemeTagRel;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.exception.city.CityInfoNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;

public interface CityEntityService {

    City findEntityById(Integer id) throws CityNotFoundException;
    CityInfo findInfoEntityByIdAndSupportLanguageId(Integer id, Integer supportLanguageId)
    throws CityInfoNotFoundException;
    void subtractThemeTagRel(City city, Tag tag, int term);
    void addThemeTagRel(City city, Tag tag, int term);
    void updateThemeOfThemeTagRel(Theme newTheme, Tag tag);
}
