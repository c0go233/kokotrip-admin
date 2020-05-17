package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.exception.region.RegionNotFoundException;

public interface RegionEntityService {

    Region findEntityById(Integer regionId) throws RegionNotFoundException;
    Region findEntityById(Integer regionId, Region other);
    void addThemeTagRel(Region region, Tag tag, int term);
    void subtractThemeTagRel(Region region, Tag tag, int term);
    void updateThemeOfThemeTagRel(Theme newTheme, Tag tag);
}
