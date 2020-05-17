package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;

import java.util.HashMap;

public interface TagEntityService {

    Tag findEntityById(Integer id) throws TagNotFoundException;
    TagInfo findInfoEntityByIdAndSupportLanguageId(Integer tagId, Integer supportLanguageId) throws
            TagInfoNotFoundException;
    HashMap<Integer, TagInfo> findAllInfoByIdAsHashMap(Integer tagId);
}
