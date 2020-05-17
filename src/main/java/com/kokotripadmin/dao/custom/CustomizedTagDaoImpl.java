package com.kokotripadmin.dao.custom;

import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.Tag_;
import com.kokotripadmin.entity.tag.Theme_;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomizedTagDaoImpl implements CustomizedTagDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Tag> findAllByEnabledAndThemeEnabled(boolean tagEnabled, boolean themeEnabled) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);

        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tagRoot);

        Predicate tagEnabledEqualPredicate = criteriaBuilder.equal(tagRoot.get(Tag_.enabled), tagEnabled);
        Predicate tagThemeEnabledEqualPredicate = criteriaBuilder.equal(tagRoot.get(Tag_.theme).get(Theme_.enabled), themeEnabled);
        Predicate tagEnabledAndTagThemeEnabledEqualPredicate = criteriaBuilder.and(tagEnabledEqualPredicate, tagThemeEnabledEqualPredicate);
        criteriaQuery.where(tagEnabledAndTagThemeEnabledEqualPredicate);

        Query<Tag> query = entityManager.unwrap(Session.class).createQuery(criteriaQuery);
        query.setCacheable(true);

        return query.getResultList();
    }
}
