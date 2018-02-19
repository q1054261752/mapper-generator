package com.dhcc.icore.tools.mybatis;

public class MysqlTableResolver
        extends BaseTableResolver {
    protected String getJdbcType(ColumnDesc desc, String tableName) {
        switch (desc.getColType()) {
            case "TINYINT":
                return "TINYINT";
            case "SMALLINT":
                return "SMALLINT";
            case "INT":
                return "INT";
            case "INTEGER":
                return "INTEGER";
            case "BIGINT":
                return "BIGINT";
            case "FLOAT":
                return "FLOAT";
            case "DOUBLE":
                return "DOUBLE";
            case "DECIMAL":
                return "DECIMAL";
            case "DATE":
                return "DATE";
            case "TIME":
                return "TIME";
            case "YEAR":
                return "YEAR";
            case "DATETIME":
                return "DATETIME";
            case "TIMESTAMP":
                return "TIMESTAMP";
            case "CHAR":
                return "CHAR";
            case "VARCHAR":
                return "VARCHAR";
            case "TINYBLOB":
                return "TINYBLOB";
            case "TINYTEXT":
                return "TINYTEXT";
            case "BLOB":
                return "BLOB";
            case "TEXT":
                return "TEXT";
            case "MEDIUMBLOB":
                return "MEDIUMBLOB";
            case "MEDIUMTEXT":
                return "MEDIUMTEXT";
            case "LONGBLOB":
                return "LONGBLOB";
            case "LONGTEXT":
                return "LONGTEXT";
            default:
                if (desc.getColType().startsWith("TIMESTAMP")) {
                    return "TIMESTAMP";
                }
                throw new IllegalArgumentException("Unsupported database type " + desc.getColType()
                        + " for " + tableName + "." + desc.getColName());
        }

    }
}
