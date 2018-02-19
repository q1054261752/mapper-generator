package com.dhcc.icore.tools.mybatis;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class DBUtils {
  private DBUtils() {
  }

  /**
   * 将数据库列名转换成驼峰命名法的java属性名
   *
   * @param colName
   * @return
   */
  public static String toJavaFiledName(String colName) {
    StringBuilder builder = new StringBuilder();

    if (colName == null) {
      throw new IllegalArgumentException("colName can't be null");
    }

    colName = colName.toLowerCase();
    int len = colName.length();
    if (len == 0) {
      throw new IllegalArgumentException("colName can't be empty");
    }

    builder.append(Character.toLowerCase(colName.charAt(0)));

    boolean needToUpper = false;
    for (int i = 1; i < len; i++) {
      char ch = colName.charAt(i);
      if (ch == '_') {
        needToUpper = true;
        if (i == len - 1) {
          // 如果最后一个字符为_那么将这个字符也追加上去
          builder.append(ch);
        }
      } else {
        if (needToUpper) {
          builder.append(Character.toUpperCase(ch));
          needToUpper = false;
        } else {
          builder.append(ch);
        }
      }
    }
    return builder.toString();
  }

  /**
   * 生成java类名
   * 类名规则如下：
   * 首字母大写，下划线忽略，下划线后第一个字母大写
   *
   * @param tableName
   * @return
   */
  public static String toJavaClassName(String tableName) {
    StringBuilder builder = new StringBuilder();

    tableName = tableName.toLowerCase();
    int len = tableName.length();
    boolean needToUpper = true;
    for (int i = 0; i < len; i++) {
      char ch = tableName.charAt(i);
      if (ch == '_') {
        needToUpper = true;
      } else {
        if (needToUpper) {
          builder.append(Character.toUpperCase(ch));
          needToUpper = false;
        } else {
          builder.append(ch);
        }
      }
    }
    return builder.toString();
  }

  public static String firstCharToUpper(String str) {
    Preconditions.checkState(StringUtils.isNotBlank(str));
    StringBuilder sb = new StringBuilder();
    sb.append(Character.toUpperCase(str.charAt(0)));
    if (str.length() > 1) {
      sb.append(str.substring(1));
    }
    return sb.toString();
  }
}
