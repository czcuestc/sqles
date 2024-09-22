package com.czcuestc.sqles.mapper.annotation;

import com.czcuestc.sqles.mapper.IndexMapper;
import com.czcuestc.sqles.mapper.proxy.MapperFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class MapperScanner extends ClassPathBeanDefinitionScanner {
    private String datasource;

    public MapperScanner(BeanDefinitionRegistry registry, String datasource) {
        super(registry, false);
        this.datasource = datasource;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        registerFilters();

        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        registerBeans(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    public void registerFilters() {
        addIncludeFilter((metadataReader, metadataReaderFactory) -> {
            try {
                String className = metadataReader.getClassMetadata().getClassName();
                Class clazz = Class.forName(className);
                if (IndexMapper.class == clazz)
                    return false;

                if (IndexMapper.class.isAssignableFrom(clazz) && clazz.isInterface()) {
                    return true;
                }
            } catch (Exception e) {
            }

            return false;
        });
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    private void registerBeans(Set<BeanDefinitionHolder> beanDefinitions) {
        if (beanDefinitions == null || beanDefinitions.isEmpty()) return;

        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.getConstructorArgumentValues().addGenericArgumentValue(datasource);
            definition.setBeanClass(MapperFactoryBean.class);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }
}
