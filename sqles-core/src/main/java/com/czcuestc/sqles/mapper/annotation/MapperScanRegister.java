package com.czcuestc.sqles.mapper.annotation;

import com.czcuestc.sqles.SqlEsClientStarter;
import com.czcuestc.sqles.client.ClientUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class MapperScanRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    public MapperScanRegister() {
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(SqlEsMapperScan.class.getName()));
        String[] basePackages = this.getPackagesToScan(attributes, "basePackages");
        String datasource = this.getAttribute(attributes, "datasource");
        SqlEsClientStarter.setBasePackages(basePackages);
        SqlEsClientStarter.start();
        MapperScanner mapperScanner = new MapperScanner(registry, datasource);
        mapperScanner.doScan(basePackages);
    }

    private String[] getPackagesToScan(AnnotationAttributes attributes, String basePackagesName) {
        if (attributes == null) {
            return null;
        } else {
            String[] basePackages = attributes.getStringArray(basePackagesName);
            return basePackages;
        }
    }

    private String getAttribute(AnnotationAttributes attributes, String name) {
        if (attributes == null) {
            return null;
        } else {
            return attributes.getString(name);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        BindResult<HashMap> config = Binder.get(environment).bind("spring.datasource", HashMap.class);
        ClientUtil.init(null, config.get());
    }
}
