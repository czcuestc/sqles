package com.sqles.demo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.czcuestc.sqles.mapper.annotation.SqlEsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Configuration
@MapperScan(basePackages = "com.sqles.demo.mapper")
@SqlEsMapperScan(basePackages = "com.sqles.demo")
public class DataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource.sqles")
    @Bean(name = "sqles")
    public DataSource dataSource() {
        DataSource dataSource= DruidDataSourceBuilder.create().build();
        return dataSource;
    }

//    @Bean(name = "sqles")
//    @ConfigurationProperties(prefix = "spring.datasource.sqles")
//    public DruidDataSource dataSource() throws SQLException {
//        DruidDataSource dataSource= new DruidDataSource();
//        dataSource.init();
//        return dataSource;
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:**/*.xml"));
//        return factoryBean.getObject();
//    }
}
