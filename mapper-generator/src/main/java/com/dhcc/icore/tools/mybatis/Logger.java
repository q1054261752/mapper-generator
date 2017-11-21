package com.dhcc.icore.tools.mybatis;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class Logger {
  private Logger() {
  }

  public static void log(String msg) {
    System.out.println(msg);
  }

  public static void log(String msg, Throwable e) {
    log(msg);
    e.printStackTrace();
  }
}
