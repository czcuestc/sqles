# SqlEs

SqlEs是Elasticsearch的客户端JDBC驱动程序，支持采用sql语法操作Elasticsearch。SqlEs构建在RestHighLevelClient，屏蔽了RestHighLevelClient接口的复杂度，可以像使用数据一样使用Elasticsearch。

[社区](http://112.124.55.179:8080/)

### 架构


![](https://cdn.nlark.com/yuque/0/2024/png/47383561/1726936757673-bd00ba62-3a6d-4cf2-bfe7-bfc3d0423c82.png)



### 特性
+ <font style="color:rgb(50, 50, 50);">零入侵</font>

<font style="color:rgb(50, 50, 50);">        应用无需改造，可以无缝集成到现有业务应用。</font>

+ <font style="color:rgb(50, 50, 50);">JDBC驱动</font>

<font style="color:rgb(50, 50, 50);">        SqlEs实现了JDBC驱动，可以无缝和其他ORM框架，数据库连接池集成。</font>

+ <font style="color:rgb(50, 50, 50);">SQL语法</font>

<font style="color:rgb(50, 50, 50);">      </font><font style="color:rgb(50, 50, 50);">SqlEs采用sql语法，并且屏蔽了Elasticsearch客户端接口的复杂度，开发人员可以轻松上手，像使用数据库一样使用Elasticsearch。</font><font style="color:rgb(50, 50, 50);">     </font>

+ <font style="color:rgb(50, 50, 50);">注解式配置</font>

<font style="color:rgb(50, 50, 50);">        SqlEs采用注解配置索引mapping信息，支持细粒度的索引字段定义。</font>

## License
SqlEs is licensed under the AGPL-3.0 license. See the [LICENSE](https://github.com/czcuestc/sqles/blob/master/LICENSE) file for details.



