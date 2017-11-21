package com.dhcc.icore.tools.mybatis;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class ColumnDesc {
  private String colName;
  private String colType;
  private int colSize;
  private int digits;
  private int nullable;

  public ColumnDesc(String colName, String colType, int colSize, int digits, int nullable) {
    this.colName = colName;
    this.colType = colType;
    this.colSize = colSize;
    this.digits = digits;
    this.nullable = nullable;
  }

  public int getDigits() {
    return digits;
  }

  public void setDigits(int digits) {
    this.digits = digits;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }

  public String getColType() {
    return colType;
  }

  public void setColType(String colType) {
    this.colType = colType;
  }

  public int getColSize() {
    return colSize;
  }

  public void setColSize(int colSize) {
    this.colSize = colSize;
  }

  public int getNullable() {
    return nullable;
  }

  public void setNullable(int nullable) {
    this.nullable = nullable;
  }
}
