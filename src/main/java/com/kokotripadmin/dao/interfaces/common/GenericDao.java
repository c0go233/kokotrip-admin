package com.kokotripadmin.dao.interfaces.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface GenericDao<Entity, PrimaryKey> extends JpaRepository<Entity, PrimaryKey> {
//
//    PrimaryKey save(Entity entity);
//    void saveOrUpdate(Entity entity);
//    void delete(Entity entity);
//    List<Entity> findAll();
//    Entity findById(PrimaryKey id);
//    void clear();
//    void flush();
}
