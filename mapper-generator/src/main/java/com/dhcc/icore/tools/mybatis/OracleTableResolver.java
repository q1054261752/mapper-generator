package com.dhcc.icore.tools.mybatis;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class OracleTableResolver extends BaseTableResolver {
  @Override
  protected String getJdbcType(ColumnDesc desc, String tableName) {


    switch (desc.getColType()) {
      case "BLOB":
        return "BLOB";
      case "CHAR":
        return "CHAR";
      case "CLOB":
        return "CLOB";
      case "DATE":
        return "DATE";
      case "DECIMAL":
        return "DECIMAL";
      case "NUMBER":
        if (desc.getDigits() != 0) {
          return "DECIMAL";
        }
        return "BIGINT";
      case "NUMERIC":
        if (desc.getDigits() != 0) {
          return "DECIMAL";
        }
        return "BIGINT";
      case "FLOAT":
        return "FLOAT";
      case "INTEGER":
        return "INTEGER";
      case "BIGINT":
        return "LONGVARCHAR";
      case "NCHAR":
        return "NCHAR";
      case "NCLOB":
        return "NCLOB";
      case "REAL":
        return "REAL";
      case "SMALLINT":
        return "SMALLINT";
      case "TIMESTAMP":
        return "TIMESTAMP";
      case "VARCHAR":
        return "VARCHAR";
      case "VARCHAR2":
        return "VARCHAR";
      case "NVARCHAR":
        return "VARCHAR";
      case "NVARCHAR2":
        return "VARCHAR";
      default:
        if (desc.getColType().startsWith("TIMESTAMP")) {
          return "TIMESTAMP";
        }
        throw new IllegalArgumentException("Unsupported database type " + desc.getColType()
            + " for " + tableName + "." + desc.getColName());
    }





  }
}
