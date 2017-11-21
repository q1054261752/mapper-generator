package com.dhcc.icore.tools.mybatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Create on 2017/3/13.
 * 获取数据表的元素据信息
 *
 * @author luo
 */
public interface TableResolver {
  /**
   * 获取数据库的表信息，其中如果includes未指明或者长度为0则默认处理所有的表
   * 如果excludes指定了则从预处理的表中移除exclude的表
   *
   * @param connection 数据库连接
   * @param catalog    数据库类型
   * @param schemaName 数据库方案名称（如果是oracle对应着用户名的大写形式，如果是mysql可以传空）
   * @param includes   需要处理的表
   * @param excludes   排除的表
   * @return
   * @throws SQLException
   */
  Map<String, Table> resolveDatabase(Connection connection,
                                     String catalog,
                                     String schemaName,
                                     String[] includes,
                                     String[] excludes

  ) throws SQLException;
}
