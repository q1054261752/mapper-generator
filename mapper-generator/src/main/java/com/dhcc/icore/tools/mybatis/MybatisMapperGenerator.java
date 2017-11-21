package com.dhcc.icore.tools.mybatis;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dhcc.icore.tools.mybatis.Logger.log;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class MybatisMapperGenerator {
	private final GeneratorConfig config;

	public MybatisMapperGenerator(GeneratorConfig config) {
		this.config = Preconditions.checkNotNull(config);
	}

	public void generate() {
		try {
			Class.forName(config.getJdbcDriver());
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Get jdbc driver failed " + config.getJdbcDriver());
		}
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", StructuredGlobbingResourceLoader.class.getName());
		Template mapperTemplate = engine.getTemplate("MybatisMapperTpl.xml");
		FileUtils.ensureDirectoryExists(config.getMapperOutDir());
		Connection connection = null;
		try {
			Map<String, List<String>> includeTables = config.getIncludeTables();
			for(Map.Entry<String, List<String>> entry : includeTables.entrySet())
			{
				String[] includes = null;
				if (entry.getValue() != null) {
					includes = new String[entry.getValue().size()];
					entry.getValue().toArray(includes);
				}
				String daoInterfaceOutputDir = config.getJavaClassOutDir() + File.separator
						+ entry.getKey();
				String entityOutputDir = daoInterfaceOutputDir + File.separator + "entity";
				String daoImplOutputDir = daoInterfaceOutputDir + File.separator + "impl";
				daoInterfaceOutputDir += File.separator + "dao";
				FileUtils.ensureDirectoryExists(daoInterfaceOutputDir);
				FileUtils.ensureDirectoryExists(entityOutputDir);
				FileUtils.ensureDirectoryExists(daoImplOutputDir);
				List<String> excludeTables = config.getExcludeTables();
				String[] excludes = null;
				if (excludeTables != null) {
					excludes = new String[excludeTables.size()];
					excludeTables.toArray(excludes);
				}
				connection = DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUserName(), config.getJdbcPassword());
				TableResolver tableResolver = findTableResolver(config.getJdbcDriver());
				Map<String, Table> resolvedTables = tableResolver.resolveDatabase(connection, config.getJdbcCatalog(),
						config.getJdbcSchema(),
						includes,
						excludes);
				for (Table table : resolvedTables.values()) {
					String entityName = DBUtils.toJavaClassName(table.getTableName());
					String entityClass = config.getDaoPackageName()+ "." + entry.getKey() + ".api.entity." + entityName + "Item";
					String daoName = entityName + config.getDaoSuffix();
					String daoClass = config.getDaoPackageName() + "." +entry.getKey() + ".dao." + daoName;
					String daoImplName = daoName + "Impl";
					String daoImplClass = config.getDaoPackageName() + "." +entry.getKey() + ".dao.impl." + daoImplName;
					table.setBeanType(entityClass);
					table.setNamespace(daoImplClass);
					
					String mapperOutputDir = config.getMapperOutDir() + File.separator +  entry.getKey();
					FileUtils.ensureDirectoryExists(mapperOutputDir);
					String mapperName = mapperOutputDir + File.separator + entityName + "." + config.getMapperSuffix() + ".xml";
					log("Generate mapper file " + mapperName);
					// 生成mapper文件
					generateMapperXml(mapperName, mapperTemplate, table);

					// 生成实体类和Dao接口以及基础实现类
					String entityClassOutFile = entityOutputDir + File.separator + entityName + "Item.java";
					log("Generate table entity " + table.getBeanType() + " to file " + entityClassOutFile);
					generateEntity(entityClassOutFile, table.getBeanType(), table);

					String daoClassOutFile = daoInterfaceOutputDir + File.separator + daoName + ".java";
					log("Generate table dao interface " + daoClass + " to file " + daoClassOutFile);
					generateDao(daoClassOutFile, daoClass, table);

					String daoImplClassOutFile = daoImplOutputDir + File.separator + daoImplName + ".java";
					log("Generate table dao impl " + daoImplClass + " to file " + daoImplClassOutFile);
					generateDaoImpl(daoImplClassOutFile, daoImplClass, table);
				}
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException("Database error " + e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
			log("全部生成成功");
		}
	}

	private void generateMapperXml(String outFile, Template mapperTemplate, Table table) {
		VelocityContext context = new VelocityContext();
		context.put("tableName", table.getTableName());
		context.put("columns", table.getColumns());
		context.put("primaryKeys", table.getPrimaryKeys());
		context.put("baseColumnList", table.getBaseColumnList());
		context.put("baseFieldList", table.getBaseFieldList());
		context.put("beanType", table.getBeanType());
		context.put("namespace", table.getNamespace());
		context.put("type", table.getType());
		if (config.getJdbcDriver().contains("oracle")) {
			// 如果是oracle的数据库 for update 指定等待时间为5秒，防止死锁时无限等待
			context.put("forUpdate", "for update wait 5");
		} else {
			context.put("forUpdate", "for update");
		}
		StringWriter stringWriter = new StringWriter();
		mapperTemplate.merge(context, stringWriter);

		FileOutputStream mapperOut = null;
		try {
			mapperOut = new FileOutputStream(outFile);
			mapperOut.write(stringWriter.toString().getBytes(config.getCharset()));
		} catch (Exception e) {
			throw new IllegalStateException("Write mapper file " + outFile + " failed : " + e.getMessage(), e);
		} finally {
			if (mapperOut != null) {
				try {
					mapperOut.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private String toJavaTypeString(String jdbcType) {
		switch (jdbcType) {
		case "CHAR":
			return "String";
		case "VARCHAR":
			return "String";
		case "LONGVARCHAR":
			return "String";
		case "NUMBER":
			return "Long";
		case "DECIMAL":
			return "BigDecimal";
		case "BIT":
			return "String";
		case "BOOLEAN":
			return "String";
		case "TINYINT":
			return "Long";
		case "SMALLINT":
			return "Long";
		case "INTEGER":
			return "Long";
		case "BIGINT":
			return "Long";
		case "REAL":
			return "BigDecimal";
		case "FLOAT":
			return "BigDecimal";
		case "DOUBLE":
			return "BigDecimal";
		case "BINARY":
			return "byte[]";
		case "VARBINARY":
			return "byte[]";
		case "LONGVARBINARY":
			return "byte[]";
		case "DATE":
			return "String";
		case "TIME":
			return "String";
		case "TIMESTAMP":
			return "String";
		case "CLOB":
			return "String";
		case "BLOB":
			return "byte[]";
		default:
			throw new UnsupportedOperationException("Not support for jdbc type " + jdbcType);
		}
	}

	private PropertyDesc resolveProperty(Column column) {
		PropertyDesc propertyDesc = new PropertyDesc();
		propertyDesc.setName(column.getJavaFieldName());
		propertyDesc.setType(toJavaTypeString(column.getJdbcType()));
		propertyDesc.setColName(column.getColName());
		propertyDesc.setSetterName("set" + DBUtils.firstCharToUpper(column.getJavaFieldName()));
		if ("Boolean".equals(propertyDesc.getType()) || "boolean".equals(propertyDesc.getType())) {
			propertyDesc.setGetterName("is" + DBUtils.firstCharToUpper(column.getJavaFieldName()));
		} else {
			propertyDesc.setGetterName("get" + DBUtils.firstCharToUpper(column.getJavaFieldName()));
		}
		return propertyDesc;
	}

	private void generateEntity(String outName, String type, Table table) {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", StructuredGlobbingResourceLoader.class.getName());
		Template entityTemplate = engine.getTemplate("EntityTpl.vm");
		VelocityContext context = new VelocityContext();
		String packageName = type.substring(0, type.lastIndexOf('.'));
		String className = type.substring(type.lastIndexOf(".") + 1);
		context.put("packageName", packageName);
		context.put("className", className);
		List<PropertyDesc> propertyDescs = new ArrayList<>();
		for (Column column : table.getColumns()) {
			PropertyDesc propertyDesc = resolveProperty(column);
			propertyDescs.add(propertyDesc);
		}
		context.put("properties", propertyDescs);
		StringWriter stringWriter = new StringWriter();
		entityTemplate.merge(context, stringWriter);

		FileOutputStream entityOut = null;
		try {
			entityOut = new FileOutputStream(outName);
			entityOut.write(stringWriter.toString().getBytes(config.getCharset()));
		} catch (Exception e) {
			throw new IllegalStateException("Write entity file " + outName + " failed : " + e.getMessage(), e);
		} finally {
			if (entityOut != null) {
				try {
					entityOut.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void generateDao(String outName, String type, Table table) {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", StructuredGlobbingResourceLoader.class.getName());
		Template entityTemplate = engine.getTemplate("DaoTpl.vm");
		VelocityContext context = new VelocityContext();
		String packageName = type.substring(0, type.lastIndexOf('.'));
		String className = type.substring(type.lastIndexOf(".") + 1);
		context.put("packageName", packageName);
		context.put("className", className);
		context.put("entityName", className.substring(0, className.length() - 3) + "Item");
		List<String> importedClass = new ArrayList<>();
		importedClass.add(config.getBasicDaoFQCN());
		importedClass.add(packageName.substring(0, packageName.lastIndexOf('.')) + ".api.entity." + className.substring(0, className.length() - 3) + "Item");
		context.put("importedClass", importedClass);

		StringWriter stringWriter = new StringWriter();
		entityTemplate.merge(context, stringWriter);

		FileOutputStream entityOut = null;
		try {
			entityOut = new FileOutputStream(outName);
			entityOut.write(stringWriter.toString().getBytes(config.getCharset()));
		} catch (Exception e) {
			throw new IllegalStateException("Write entity file " + outName + " failed : " + e.getMessage(), e);
		} finally {
			if (entityOut != null) {
				try {
					entityOut.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void generateDaoImpl(String outName, String type, Table table) {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", StructuredGlobbingResourceLoader.class.getName());
		Template entityTemplate = engine.getTemplate("ImplTpl.vm");
		VelocityContext context = new VelocityContext();
		String packageName = type.substring(0, type.lastIndexOf('.'));
		String className = type.substring(type.lastIndexOf(".") + 1);
		String daoName = className.substring(0, className.length() - 4);
		String entityName = className.substring(0, className.length() - 7) + "Item";
		context.put("packageName", packageName);
		context.put("className", className);
		context.put("daoName", daoName);
		context.put("daoName1", daoName.substring(0, 1).toLowerCase() + daoName.substring(1));
		context.put("entityName", entityName);
		List<String> importedClass = new ArrayList<>();
		importedClass.add(config.getBasicDaoImplFQCN());
		String daoPackage = packageName.substring(0, packageName.lastIndexOf('.'));
		importedClass.add(daoPackage + "." + daoName);
		String entityPackage = daoPackage.substring(0, daoPackage.lastIndexOf('.'));
		importedClass.add(entityPackage + ".api.entity." + entityName);
		importedClass.add("org.springframework.stereotype.Repository");
		context.put("importedClass", importedClass);

		StringWriter stringWriter = new StringWriter();
		entityTemplate.merge(context, stringWriter);

		FileOutputStream entityOut = null;
		try {
			entityOut = new FileOutputStream(outName);
			entityOut.write(stringWriter.toString().getBytes(config.getCharset()));
		} catch (Exception e) {
			throw new IllegalStateException("Write entity file " + outName + " failed : " + e.getMessage(), e);
		} finally {
			if (entityOut != null) {
				try {
					entityOut.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private TableResolver findTableResolver(String jdbcDriver) {
		if (jdbcDriver.contains("oracle")) {
			return new OracleTableResolver();
		}
		throw new UnsupportedOperationException("Now just support oracle database");
	}
}
