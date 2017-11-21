package com.dhcc.icore.tools.mybatis;

/**
 * Create on 2017/3/14.
 *
 * @author luo
 */
public class PropertyDesc {
  private String colName;
  private String type;
  private String name;
  private String getterName;
  private String setterName;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGetterName() {
    return getterName;
  }

  public void setGetterName(String getterName) {
    this.getterName = getterName;
  }

  public String getSetterName() {
    return setterName;
  }

  public void setSetterName(String setterName) {
    this.setterName = setterName;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }
}
