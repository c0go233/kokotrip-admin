package com.kokotripadmin.config.property;


import org.springframework.stereotype.Component;

@Component
public class HibernateProperty {

    private String entityPackageToScan;
    private String hibernateDialect;
    private String showSql;

    public String getEntityPackageToScan() {
        return entityPackageToScan;
    }

    public void setEntityPackageToScan(String entityPackageToScan) {
        this.entityPackageToScan = entityPackageToScan;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }
}
