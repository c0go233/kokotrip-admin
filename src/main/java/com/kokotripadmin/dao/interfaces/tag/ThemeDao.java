package com.kokotripadmin.dao.interfaces.tag;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tag.Theme;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeDao extends GenericDao<Theme, Integer>, JpaSpecificationExecutor<Theme> {
}
