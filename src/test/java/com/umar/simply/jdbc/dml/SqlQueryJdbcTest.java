package com.umar.simply.jdbc.dml;

import com.umar.simply.jdbc.JdbcUtil;

/**
 *
 * @author umar
 */
public class SqlQueryJdbcTest {
    final static String driverClass = "org.h2.Driver";
    final static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    final static String user = "sa";
    final static String passwd = "sa";
    final static JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);
}
