/*
create table PERSON
(
    //id bigint primary key auto_increment, //mysql
    id bigint auto_increment primary key, //h2 database
    firstname varchar(20),
    lastname varchar(20),
    adult tinyint(1),
    age int(3),
    email varchar(25),
    city varchar(15),
    country varchar(20),
    created timestamp
);
*/


package com.umar.simply.jdbc.dml;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.EX_SCHEMA;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Person.TblPerson.*;
import static java.util.Arrays.asList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InsertOpJdbcTest  {

    //String driverClass = "com.mysql.cj.jdbc.Driver";
    //String url = "jdbc:mysql://localhost:3306/ex";
    static String driverClass = "org.h2.Driver";
    static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    static String user = "sa";
    static String passwd = "sa";
    static JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);
    
    @AfterAll
    public static void clean(){
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom(EX_SCHEMA +"." +person).where().anyColumnValues(set(fname,"Tina"));
        System.out.println(operation.getSQL());
        try (Connection connection = util.getConnection();
            PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            operation.fill(ps).executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInsertOp() {
        InsertOp insert = InsertOp.create();
        insert.intoTable(EX_SCHEMA +"." +person).columnValues(asList(set(fname,"Tina"), set(lname,"Turner"), set(email,"tina@rediffmail.com"), set(adult, true)));
        System.out.println(insert.getSQL());
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert.getSQL())) {
            int result = insert.fill(ps).executeUpdate();
            Assertions.assertTrue(result > 0);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}