package com.dhcc.icore.tools.mybatis;

import java.io.File;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class FileUtils {
  private FileUtils() {
  }

  public static void ensureDirectoryExists(String dirName) {
    File dir = new File(dirName);
    if (!dir.exists()) {
      if (!dir.mkdirs()) {
        throw new IllegalStateException(String.format("Make directory %s failed", dirName));
      }
    }
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException(String.format("Directory path %s is not a directory", dirName));
    }
  }
}
