package com.kokotripadmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.config.property.ServletProperty;
import com.kokotripadmin.config.modelmapperconfig.ModelMapperConfig;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "com.kokotripadmin")
public class ServletConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServletConfig.class);

    @Autowired private ServletProperty servletProperty;

    @Bean
    public static PropertyOverrideConfigurer propertyOverrideConfigurer() {
        PropertyOverrideConfigurer configurer = new PropertyOverrideConfigurer();

        configurer.setLocations(new ClassPathResource("configuration.properties"),
                                new ClassPathResource("credential.properties"));
//        configurer.setLocation(new ClassPathResource("configuration.properties"));
//        configurer.setLocation(new ClassPathResource("credential.properties"));
        return configurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix(servletProperty.getViewResolverPrefix());
//        viewResolver.setSuffix(servletProperty.getViewResolverSuffix());
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html; charset=UTF-8");
        return viewResolver;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        ModelMapperConfig.set(modelMapper);

        return modelMapper;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setTimeZone(TimeZone.getDefault());
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Convert convert() {
        Convert convert = new Convert();
        return convert;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(1000000);
        return resolver;
    }

}
