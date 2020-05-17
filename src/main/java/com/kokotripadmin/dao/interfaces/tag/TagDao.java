package com.kokotripadmin.dao.interfaces.tag;

import com.kokotripadmin.dao.custom.CustomizedTagDao;
import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends GenericDao<Tag, Integer>, JpaSpecificationExecutor<Tag>, CustomizedTagDao {
}
