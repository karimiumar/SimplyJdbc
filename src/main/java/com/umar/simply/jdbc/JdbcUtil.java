package com.umar.simply.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {

    private static JdbcUtil util;
    private final String driverClass;
    private final String url;
    private final String user;
    private final String password;

    private JdbcUtil(String driverClassName, String url, String user, String passwd){
        this.driverClass = driverClassName;
        this.url = url;
        this.user = user;
        this.password = passwd;
    }

    public static JdbcUtil init(String driverClassName, String url, String user, String passwd){
        if(null == util){
            synchronized (JdbcUtil.class) {
                util = new JdbcUtil(driverClassName,url,user,passwd);
            }
        }
        return util;
    }

    public Connection getConnection() {
        try {
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException("Unable to establish Connection-->", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("ClassNotFoundException->", ex);
        }
    }
}
