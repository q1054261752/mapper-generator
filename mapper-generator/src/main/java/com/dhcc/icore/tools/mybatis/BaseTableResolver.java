package com.dhcc.icore.tools.mybatis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.dhcc.icore.tools.mybatis.DBUtils.toJavaFiledName;
import static com.dhcc.icore.tools.mybatis.Logger.log;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public abstract class BaseTableResolver implements TableResolver {
	public static final String COL_TABLE_NAME = "TABLE_NAME";
	public static final String COL_COL_NAME = "COLUMN_NAME";
	public static final String COL_TYPE_NAME = "TYPE_NAME";
	public static final String COL_COL_SIZE = "COLUMN_SIZE";
	public static final String COL_DECIMAL_DIGITS = "DECIMAL_DIGITS";
	public static final String COL_NULLABLE = "NULLABLE";

	@Override
	public Map<String, Table> resolveDatabase(Connection connection,
			String catalog,
			String schemaName,
			String[] includes,
			String[] excludes
			) throws SQLException {
		DatabaseMetaData dbMeta = connection.getMetaData();
		Map<String, Table> tableInfos = new HashMap<>();
		Set<String> tableToResolve = new HashSet<>();
		Set<String> peeResolveTable = new HashSet<>();
		Set<String> viewSet = new HashSet<>();
		Set<String> tableSet = new HashSet<>();

		// 获取所有的表和视图信息
		ResultSet tables = dbMeta.getTables(catalog, schemaName, "%", new String[]{"TABLE"});
		try {
			while (tables.next()) {
				String tableName = tables.getString(COL_TABLE_NAME);
				peeResolveTable.add(tableName.toUpperCase());
				tableSet.add(tableName.toUpperCase());
			}
		} finally {
			tables.close();
		}

		ResultSet views = dbMeta.getTables(catalog, schemaName, "%", new String[]{"VIEW"});
		try {
			while (views.next()) {
				String tableName = views.getString(COL_TABLE_NAME);
				peeResolveTable.add(tableName.toUpperCase());
				viewSet.add(tableName.toUpperCase());
			}
		} finally {
			views.close();
		}

		if (includes != null && includes.length > 0) {
			// 如果includes声明了，那么使用include中的表名
			for (String tableName : includes) {
				tableToResolve.add(tableName.toUpperCase());
			}
		} else {
			// 否则使用所有的表名作为预处理的表
			tableToResolve.addAll(peeResolveTable);
		}

		log("Tables to resolve are " + tableToResolve);

		if (excludes != null && excludes.length > 0) {
			for (String excludeTableName : excludes) {
				if (tableToResolve.contains(excludeTableName.toUpperCase())) {
					tableToResolve.remove(excludeTableName.toUpperCase());
				}
			}
		}

		for (String tableName : tableToResolve) {
			Table table = new Table();
			table.setTableName(tableName);
			// 指定是table还是view，默认为table
			if (viewSet.contains(tableName)) {
				table.setType("VIEW");
			} else {
				table.setType("TABLE");
			}

			Map<String, Column> colInfos = new HashMap<>();
			// 获取表的列信息
			ResultSet cols = dbMeta.getColumns(catalog, schemaName, tableName, "%");
			List<Column> storedCols = new ArrayList<>();
			try {
				while (cols.next()) {
					String colName = cols.getString(COL_COL_NAME);
					String colType = cols.getString(COL_TYPE_NAME);
					int dataSize = cols.getInt(COL_COL_SIZE);
					int digits = cols.getInt(COL_DECIMAL_DIGITS);
					int nullable = cols.getInt(COL_NULLABLE);
					ColumnDesc desc = new ColumnDesc(colName, colType, dataSize, digits, nullable);
					String jdbcType = getJdbcType(desc, tableName);
					Column col = new Column(colName, colName.toLowerCase(), jdbcType);
					storedCols.add(col);
					colInfos.put(colName, col);
				}
				String baseColList = "";
				String baseFieldList = "";
				int nCols = storedCols.size();
				int i = 0;
				String colName = "";
				for (Column col : storedCols) {
					i++;
					if("_ID".equals(col.getColName().toUpperCase())){
						colName = "\"" + col.getColName() + "\"";
					}
					else {
						colName = col.getColName();
					}
					if (i == nCols) {
						baseColList += colName;
						baseFieldList += "#{" + col.getJavaFieldName() + ",jdbcType=" + col.getJdbcType() + "}";
					} else {
						baseColList += colName + ",";
						baseFieldList += "#{" + col.getJavaFieldName() + ",jdbcType=" + col.getJdbcType() + "},";
					}
				}
				table.setColumns(storedCols);
				table.setBaseColumnList(baseColList);
				table.setBaseFieldList(baseFieldList);
			} finally {
				cols.close();
			}

			// 获取主键
			List<Column> storedPks = new ArrayList<>();
			ResultSet pks = dbMeta.getPrimaryKeys(catalog, schemaName, tableName);
			try {
				while (pks.next()) {
					String colName = pks.getString(COL_COL_NAME);
					storedPks.add(colInfos.get(colName));
				}
				table.setPrimaryKeys(storedPks);
			} finally {
				pks.close();
			}
			tableInfos.put(tableName, table);
		}
		return tableInfos;
	}

	/**
	 * 获取mybatis对应的jdbc类型
	 * 该函数的返回不能为空，如果不存在对应的类型则抛出IllegalArgumentException
	 *
	 * @param desc 数据库列的描述
	 * @return
	 */
	protected abstract String getJdbcType(ColumnDesc desc, String tableName);
}
