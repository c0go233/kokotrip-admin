package com.kokotripadmin.dao.custom;

import com.kokotripadmin.entity.tag.Tag;
import java.util.List;

public interface CustomizedTagDao {

    List<Tag> findAllByEnabledAndThemeEnabled(boolean tagEnabled, boolean themeEnabled);
}
