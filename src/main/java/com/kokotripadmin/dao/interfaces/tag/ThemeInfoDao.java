package com.kokotripadmin.dao.interfaces.tag;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.tag.ThemeInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeInfoDao extends GenericDao<ThemeInfo, Integer>, JpaSpecificationExecutor<ThemeInfo> {
}
