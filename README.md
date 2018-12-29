
## 介绍
该项目是由mybatis-generator-core 1.3.5修改而来，主要添加中文注释信息
## 使用方式
打成jar包，加入到本地仓库,替换mybatis-generator-core包
<br>

## 修改源码说明(原版本没有的功能)
1.数据表的备注信息的添加：在FullyQualifiedTable类中添加remark字段,并在org.mybatis.generator.internal.db.DatabaseIntrospector类calculateIntrospectedTables方法,添加一段获取数据库备注的代码<br>
```java
  //设置数据库表的备注信息
  //start
  Statement stmt = this.databaseMetaData.getConnection().createStatement();
  ResultSet rs = stmt.executeQuery(
          new StringBuilder()
          .append("SHOW TABLE STATUS LIKE '")
          .append(atn.getTableName())
          .append("'")
          .toString());
  while (rs.next())
      table.setRemark(rs.getString("COMMENT"));
  closeResultSet(rs);
  stmt.close();
  //end
```
<br>
2..非model类Example的注释方法的添加，方法名addExampleClassComment(TopLevelClass topLevelClass)<br>
3.重构部分org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl包里getGeneratedJavaFiles方法<br>
4.详细中文注释的添加，入口包函数在org.mybatis.generator.api.ShellRunner<br>
5.生成的中文注释信息可在修DefaultCommentGenerator类修改<br>
6.增加MybatisServicePlugin：service层的代码生成，个人觉得不完美，因为业务会变，所以service层也会变，仅供学习参考<br>
7.增加MapperPlugin：Mapper层有大量生成的重复方法，所以增加了统一继承IMapper接口实现<br>
8.为IMapper接口加入批量插入数据的方法<br>
9.删除ibatis2内容，并删除CaseInsensitiveLikePlugin插件（这个插件用来在XXXExample类中生成大小写敏感的LIKE方法插件本身用处不大，但是我们可以通过这个插件学习给XXXExample类添加额外的方法）），因此引用了ibatis2
10.为IMapper接口加入批量更新数据的方法<br>
11.生成的代码符合阿里规范<br>