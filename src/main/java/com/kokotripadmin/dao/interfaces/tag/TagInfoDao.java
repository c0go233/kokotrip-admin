package com.kokotripadmin.dao.interfaces.tag;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tag.TagInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagInfoDao extends GenericDao<TagInfo, Integer>, JpaSpecificationExecutor<TagInfo> {
}
