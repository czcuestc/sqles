package com.czcuestc.sqles.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TextField {
    boolean index() default true;

    boolean store() default false;

    /**
     * 通常情况下，text类型的字段在 Elasticsearch 中是被分析的，用于全文搜索。当需要对text类型字段进行聚合（aggregation）、排序（sorting）或者在脚本（scripts）中基于词项（terms）进行操作时，就需要启用fieldData。
     * 例如，对一篇文章的内容（text类型）字段进行词频统计的聚合操作，或者按照文章内容中的某个关键词的出现次数进行排序时，就需要fieldData。
     * 启用fieldData会消耗大量的堆内存，尤其是对于大的文本字段或者有大量唯一值的字段。在使用时需要谨慎评估内存使用情况，并考虑是否有其他替代方案（如使用docValues）
     *
     * @return
     */
    boolean fieldData() default false;

    /**
     * 一、数值类型
     * long、integer、short、byte、double、float、half_float、scaled_float：这些数值类型都可以利用docValues。在进行范围查询、排序、聚合等操作时，docValues可以提高性能。例如，在对大量数据进行数值范围筛选或者基于数值字段的排序操作时，docValues可以让这些操作更加高效。
     * 二、日期类型
     * date和date_nanos：对于日期相关的操作，如按日期范围查询、基于日期的聚合统计等，docValues能发挥作用。比如在日志分析场景中，按日期字段筛选特定时间段内的日志或者统计每天的日志数量等操作，都可以从docValues中受益。
     * 三、布尔类型
     * boolean：在进行布尔类型的过滤和聚合操作时，docValues可以被使用。例如，统计满足某个布尔条件的文档数量或者对布尔类型字段进行分组聚合等操作。
     * 四、关键字类型
     * keyword：在基于关键字类型字段进行精确匹配查询、聚合操作（如按关键字分组统计）、排序等操作时，docValues能够提高效率。比如在电商系统中，对商品分类（关键字类型字段）进行统计分析或者排序等操作。
     *
     * @return
     */
    boolean docValues() default false;

    /**
     * 主要对文本类型（如text、keyword）有效。
     * 一、对文本类型的作用
     * text类型：
     * 当对一个包含text类型的字段应用标准化器时，在索引和搜索过程中，该字段的文本内容会经过标准化器定义的处理。例如，如果使用一个将文本转换为小写的标准化器，那么输入的各种大小写混合的文本在索引时会被转换为小写形式存储，在搜索时查询文本也会经过同样的处理，从而提高搜索的准确性和一致性。
     * 对于复杂的文本分析场景，标准化器可以帮助去除文本中的噪音，使得搜索结果更加准确。例如，可以使用标准化器去除特殊字符、转换为特定的编码格式等。
     * keyword类型：
     * keyword类型通常用于存储精确值，如标签、分类等。标准化器可以对keyword类型的字段进行预处理，使得在进行精确匹配搜索时更加一致。例如，可以使用标准化器将关键词转换为特定的格式，去除多余的空格或特殊字符等。
     *
     * @return
     */
    String normalizer() default "";

    String nullValue() default "";

    /**
     * norms（标准化因子）主要支持文本类型（如 text 和 keyword）的数据。
     * 对于数值类型（如 integer、long、short、byte、float、double等），通常不使用 norms，因为数值类型的相关性计算通常基于数值本身的大小关系，而不是像文本类型那样基于词频和逆文档频率等因素。
     * 日期类型（如 date）一般也不使用 norms进行相关性计算。
     *
     * @return
     */
    boolean norms() default false;

    String searchAnalyzer() default "";

    String analyzer() default "";

    IndexOptions indexOptions() default IndexOptions.FREQS;
}
