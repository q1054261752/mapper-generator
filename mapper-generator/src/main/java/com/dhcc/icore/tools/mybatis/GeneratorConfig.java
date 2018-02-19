package com.dhcc.icore.tools.mybatis;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class GeneratorConfig {
  private String javaClassOutDir;
  private String daoPackageName;
  private String daoSuffix; // dao接口类的追加部分，默认为Dao
  private String mapperSuffix;
  private String mapperOutDir;
  private String jdbcUrl;
  private String jdbcDriver;
  private String jdbcUserName;
  private String jdbcPassword;
  private String jdbcCatalog;
  private String jdbcSchema;
  private String charset;
  private String basicDaoFQCN;
  private String basicDaoImplFQCN;
  private HashMap<String, List<String>> includeTables;
  private List<String> excludeTables;

  public void afterPropertiesSet() {
    Preconditions.checkNotNull(javaClassOutDir, "javaClassOutDir");
    Preconditions.checkNotNull(daoPackageName, "daoPackageName");
    Preconditions.checkNotNull(mapperOutDir, "mapperOutDir");
    Preconditions.checkNotNull(jdbcDriver, "jdbcDriver");
    Preconditions.checkNotNull(jdbcUrl, "jdbcUrl");
    Preconditions.checkNotNull(jdbcUserName, "jdbcUserName");
    Preconditions.checkNotNull(jdbcPassword, "jdbcPassword");
    if (StringUtils.isEmpty(mapperSuffix)) {
      this.mapperSuffix = "Mapper";
    }
    if (StringUtils.isEmpty(daoSuffix)) {
      this.daoSuffix = "Dao";
    }
    if (StringUtils.isEmpty(charset)) {
      this.charset = "utf-8";
    }
    if (StringUtils.isEmpty(jdbcCatalog)) {
      this.jdbcCatalog = null;
    }
    if (StringUtils.isEmpty(jdbcSchema)) {
      this.jdbcSchema = null;
    }
  }

  public String getDaoPackageName() {
    return daoPackageName;
  }

  public void setDaoPackageName(String daoPackageName) {
    this.daoPackageName = daoPackageName;
  }

  public String getMapperSuffix() {
    return mapperSuffix;
  }

  public void setMapperSuffix(String mapperSuffix) {
    this.mapperSuffix = mapperSuffix;
  }

  public String getMapperOutDir() {
    return mapperOutDir;
  }

  public void setMapperOutDir(String mapperOutDir) {
    this.mapperOutDir = mapperOutDir;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public String getJdbcDriver() {
    return jdbcDriver;
  }

  public void setJdbcDriver(String jdbcDriver) {
    this.jdbcDriver = jdbcDriver;
  }

  public String getJdbcUserName() {
    return jdbcUserName;
  }

  public void setJdbcUserName(String jdbcUserName) {
    this.jdbcUserName = jdbcUserName;
  }

  public String getJdbcCatalog() {
    return jdbcCatalog;
  }

  public void setJdbcCatalog(String jdbcCatalog) {
    this.jdbcCatalog = jdbcCatalog;
  }

  public String getJdbcSchema() {
    return jdbcSchema;
  }

  public void setJdbcSchema(String jdbcSchema) {
    this.jdbcSchema = jdbcSchema;
  }

  public HashMap<String, List<String>> getIncludeTables() {
    return includeTables;
  }

  public void setIncludeTables(HashMap<String, List<String>> includeTables) {
    this.includeTables = includeTables;
  }

  public List<String> getExcludeTables() {
    return excludeTables;
  }

  public void setExcludeTables(List<String> excludeTables) {
    this.excludeTables = excludeTables;
  }

  public String getJdbcPassword() {
    return jdbcPassword;
  }

  public void setJdbcPassword(String jdbcPassword) {
    this.jdbcPassword = jdbcPassword;
  }

  public String getDaoSuffix() {
    return daoSuffix;
  }

  public void setDaoSuffix(String daoSuffix) {
    this.daoSuffix = daoSuffix;
  }

  public String getJavaClassOutDir() {
    return javaClassOutDir;
  }

  public void setJavaClassOutDir(String javaClassOutDir) {
    this.javaClassOutDir = javaClassOutDir;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getBasicDaoFQCN() {
    return basicDaoFQCN;
  }

  public void setBasicDaoFQCN(String basicDaoFQCN) {
    this.basicDaoFQCN = basicDaoFQCN;
  }

  public String getBasicDaoImplFQCN() {
    return basicDaoImplFQCN;
  }

  public void setBasicDaoImplFQCN(String basicDaoImplFQCN) {
    this.basicDaoImplFQCN = basicDaoImplFQCN;
  }
}
