package com.kokotripadmin.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.kokotripadmin.config.property.AmazonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class AmazonConfig {

    @Autowired
    private AmazonProperty amazonProperty;


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(amazonProperty.getAccessKey(),
                                                             amazonProperty.getSecretKey());



        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                                                 .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                                 .withRegion(Regions.AP_NORTHEAST_2)
                                                 .build();
        return amazonS3;
    }
}
