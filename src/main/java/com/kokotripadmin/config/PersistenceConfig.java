package com.kokotripadmin.config;

import com.kokotripadmin.config.property.ConnectionPoolProperty;
import com.kokotripadmin.config.property.HibernateProperty;
import com.kokotripadmin.config.property.JdbcProperty;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.kokotripadmin.dao")
public class PersistenceConfig {

    @Autowired private HibernateProperty hibernateProperty;
    @Autowired private JdbcProperty jdbcProperty;
    @Autowired private ConnectionPoolProperty connectionPoolProperty;

    private static final Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        setJdbcConfig(dataSource);
        setConnectionPoolConfig(dataSource);
        return dataSource;
    }

    private void setConnectionPoolConfig(ComboPooledDataSource dataSource) {
        dataSource.setInitialPoolSize(connectionPoolProperty.getInitialPoolSize());
        dataSource.setMinPoolSize(connectionPoolProperty.getMinPoolSize());
        dataSource.setMaxPoolSize(connectionPoolProperty.getMaxPoolSize());
        dataSource.setMaxIdleTime(connectionPoolProperty.getMaxIdleTime());
    }

    private void setJdbcConfig(ComboPooledDataSource dataSource) {
        try {
            dataSource.setDriverClass(jdbcProperty.getDriver());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        logger.info("Access Database with jdbc.url=" + jdbcProperty.getUrl() + " | jdbc.user=" + jdbcProperty.getUser());


        dataSource.setJdbcUrl(jdbcProperty.getUrl());
        dataSource.setUser(jdbcProperty.getUser());
        dataSource.setPassword(jdbcProperty.getPassword());
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(hibernateProperty.getEntityPackageToScan());
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());

        return em;
    }


//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan(hibernateProperty.getEntityPackageToScan());
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        return sessionFactory;
//    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateProperty.getHibernateDialect());
        hibernateProperties.setProperty("show_sql", hibernateProperty.getShowSql());
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
        hibernateProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", Boolean.TRUE.toString());

        return hibernateProperties;
    }
}
