package com.kokotripadmin.dao.interfaces.common;


import com.kokotripadmin.dao.custom.CustomizedSupportLanguageDao;
import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.common.SupportLanguage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

//public interface SupportLanguageDao extends GenericDao<SupportLanguageEntity, Integer> {
//}

@Repository
public interface SupportLanguageDao extends GenericDao<SupportLanguage, Integer>, JpaSpecificationExecutor<SupportLanguage>, CustomizedSupportLanguageDao {


    @Override
    @Cacheable("findAllSupportLanguages")
    List<SupportLanguage> findAll(Specification<SupportLanguage> specification);



}