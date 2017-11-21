package com.dhcc.icore.tools.mybatis;

import java.util.List;

/**
 * Create on 2017/3/9.
 *
 * @author luo
 */
public class Table {
  private String tableName;
  private List<Column> columns;
  private List<Column> primaryKeys;

  private String baseColumnList;
  private String baseFieldList;
  private String beanType;
  private String namespace;
  private String type;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public List<Column> getPrimaryKeys() {
    return primaryKeys;
  }

  public void setPrimaryKeys(List<Column> primaryKeys) {
    this.primaryKeys = primaryKeys;
  }

  public String getBaseColumnList() {
    return baseColumnList;
  }

  public void setBaseColumnList(String baseColumnList) {
    this.baseColumnList = baseColumnList;
  }

  public String getBaseFieldList() {
    return baseFieldList;
  }

  public void setBaseFieldList(String baseFieldList) {
    this.baseFieldList = baseFieldList;
  }

  public String getBeanType() {
    return beanType;
  }

  public void setBeanType(String beanType) {
    this.beanType = beanType;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Table{");
    sb.append("tableName='").append(tableName).append('\'');
    sb.append(", columns=").append(columns);
    sb.append(", primaryKeys=").append(primaryKeys);
    sb.append(", baseColumnList='").append(baseColumnList).append('\'');
    sb.append(", baseFieldList='").append(baseFieldList).append('\'');
    sb.append(", beanType='").append(beanType).append('\'');
    sb.append(", namespace='").append(namespace).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
