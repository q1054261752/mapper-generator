# [0.java.io.File中的绝对路径和相对路径](http://blog.csdn.net/evilcry2012/article/details/51602220)



# [1.Google Guava入门](https://www.cnblogs.com/willsuna/p/5224504.html)


# [2.YAML语言教程](http://www.ruanyifeng.com/blog/2016/07/yaml.html)



# 3.项目介绍
1. Oracle数据库生成数据库操作<br>
MybatisMapperGenerator
2. Mysql数据库生成数据库操作<br>
MybatisMysqlMapperGenerator.java
3. 使用的方法
使用mvn命令 mvn install -DskipTests
生成三个jar包
mapper-generator-0.0.1-SNAPSHOT-jar-with-dependencies.jar
是可以使用的，在该jar的同目录下添加table_one_mysql.yml等模板文件

**用户须知**:<br>
1.  单模块：<br>
includeTables: {"module":["table"]}<br>
2. 多模块：<br>
includeTables: {"module1":["table1","table2"],
"module2":["table3","table4"]}

将module替换成表所属模块，如：de、ln、com
将table替换成表名

1. 打开example.yml，编辑 includeTables
2. 执行 java -jar mapper-generator-0.0.1-SNAPSHOT.jar table_one_mysql.yml
