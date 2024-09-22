package com.czcuestc.sqles.conf;

import com.czcuestc.sqles.SqlEsClientStarter;
import com.czcuestc.sqles.annotation.Document;
import com.czcuestc.sqles.annotation.Setting;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.EntityInfoManager;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import com.czcuestc.sqles.util.IndexConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private static String RESOURCE_PATTERN = "/**/*.class";

    public static void load() {
        List<IndexInfo> indexInfos = new ArrayList<>();
        createIndexInfo(indexInfos);
        load(indexInfos);
    }

    private static void load(List<IndexInfo> tableInfoList) {
        for (IndexInfo indexInfo : tableInfoList) {
            if (IndexInfoManager.getInstance().containsKey(indexInfo.getIndexName()))
                continue;

            IndexInfoManager.getInstance().put(indexInfo);
            EntityInfoManager.getInstance().put(indexInfo.getEntityInfo());
        }
    }

    public static void createIndexInfo(List<IndexInfo> indexInfos) {
        String[] basePackages = SqlEsClientStarter.getBasePackages();
        if (basePackages == null || basePackages.length == 0) return;

        for (String basePackage : basePackages) {
            createIndexInfo(basePackage, indexInfos);
        }
    }

    public static void createIndexInfo(String basePackage, List<IndexInfo> indexInfos) {
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(basePackage) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            //MetadataReader 的工厂类
            MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                //用于读取类信息
                MetadataReader reader = readerfactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);

                Annotation[] annotations = clazz.getAnnotations();
                if (annotations == null || annotations.length == 0)
                    continue;

                Document document = clazz.getAnnotation(Document.class);
                if (document == null) continue;

                String indexName = document.value();
                if (StringUtil.isEmpty(indexName)) {
                    indexName = StringUtil.camelToUnderline(clazz.getSimpleName());
                } else {
                    indexName = StringUtil.camelToUnderline(indexName);
                }

                IndexInfo indexInfo = new IndexInfo();
                indexInfo.setIndexName(indexName);
//                String alias = document.alias();
//                if (!StringUtil.isEmpty(alias)) {
//                    indexInfo.setAliasName(alias);
//                }

                EntityInfo entityInfo = new EntityInfo(clazz);
                entityInfo.setIndexName(indexName);

                indexInfo.setEntityInfo(entityInfo);

                Setting setting = clazz.getAnnotation(Setting.class);
                if (setting != null) {
                    if (setting.shards() != IndexConstants.NUMBER_ONE) {
                        indexInfo.setShards(setting.shards());
                    }
                    if (setting.replicas() != IndexConstants.NUMBER_ONE) {
                        indexInfo.setReplicas(setting.replicas());
                    }
                    if (!StringUtil.isEmpty(setting.refreshInterval())) {
                        indexInfo.setRefreshInterval(setting.refreshInterval());
                    }
                    if (setting.maxResultWindow() != IndexConstants.MAX_RESULT_WINDOW) {
                        indexInfo.setMaxResultWindow(setting.maxResultWindow());
                    }

                    indexInfo.setSetting(true);
                }

                indexInfos.add(indexInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
