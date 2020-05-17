package com.kokotripadmin.dao.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.SupportLanguage_;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


//https://stackoverflow.com/questions/53209345/spring-data-jpa-how-to-build-a-generic-repository-for-multiple-entities
public class CustomizedSupportLanguageDaoImpl implements CustomizedSupportLanguageDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void customizedMethod() {
    }

    public List<SupportLanguage> findAllByEnabled(boolean supportLanguageEnabled) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<SupportLanguage> criteriaQuery = criteriaBuilder.createQuery(SupportLanguage.class);
        Root<SupportLanguage> supportLanguageRoot = criteriaQuery.from(SupportLanguage.class);
        criteriaQuery.select(supportLanguageRoot);

        Predicate predicate = criteriaBuilder.equal(supportLanguageRoot.get(SupportLanguage_.enabled), supportLanguageEnabled);
        criteriaQuery.where(predicate);

        Query<SupportLanguage> query = session.createQuery(criteriaQuery);
        query.setCacheable(true);

        return query.getResultList();
    }

    @Override
    public SupportLanguage findDefaultSupportLanguage() throws SupportLanguageNotFoundException {
//        Session session = entityManager.unwrap(Session.class);
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//
//        CriteriaQuery<SupportLanguage> criteriaQuery = criteriaBuilder.createQuery(SupportLanguage.class);
//        Root<SupportLanguage> cityRoot = criteriaQuery.from(SupportLanguage.class);
//        criteriaQuery.select(cityRoot);

//        Predicate predicate = criteriaBuilder.equal(cityRoot.get(SupportLanguageEntity_.id), SupportLanguageEnum.Korean.getId());
//
//        criteriaQuery.where(predicate);

//        SupportLanguage korean = session.createQuery(criteriaQuery).getSingleResult();
//        if (korean == null) throw new SupportLanguageNotFoundException(SupportLanguageEnum.Korean.getId());
//        else return korean;
        return null;
    }


}


