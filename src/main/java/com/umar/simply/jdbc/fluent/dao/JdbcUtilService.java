package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.JdbcUtil;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUtilService {
    public static Connection getConnection() {
        try {
            final InputStream is = JdbcUtilService.class.getResourceAsStream("postgresdb.properties");
            Properties properties = new Properties();
            properties.load(is);
            String driverClass = properties.getProperty("driverClass");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String passwd = properties.getProperty("passwd");
            final JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);
            return util.getConnection();
        } catch (IOException ex) {
            Logger.getLogger(JdbcUtilService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        throw new RuntimeException("Unable to get Database Connection.");
    }
}
