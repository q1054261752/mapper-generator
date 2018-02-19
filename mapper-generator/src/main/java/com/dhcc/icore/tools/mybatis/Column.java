package com.dhcc.icore.tools.mybatis;

/**
 * Create on 2017/3/9.
 *
 * @author luo
 */
public class Column {

  private String colName;
  private String javaFieldName;
  private String jdbcType;

  public Column(String colName, String javaFieldName, String jdbcType) {
    this.colName = colName;
    this.javaFieldName = javaFieldName;
    this.jdbcType = jdbcType;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }

  public String getJavaFieldName() {
    return javaFieldName;
  }

  public void setJavaFieldName(String javaFieldName) {
    this.javaFieldName = javaFieldName;
  }

  public String getJdbcType() {
    return jdbcType;
  }

  public void setJdbcType(String jdbcType) {
    this.jdbcType = jdbcType;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Column{");
    sb.append("colName='").append(colName).append('\'');
    sb.append(", javaFieldName='").append(javaFieldName).append('\'');
    sb.append(", jdbcType='").append(jdbcType).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
