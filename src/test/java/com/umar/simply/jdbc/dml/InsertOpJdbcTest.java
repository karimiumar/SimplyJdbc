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
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertOpJdbcTest  {

    //String driverClass = "com.mysql.cj.jdbc.Driver";
    //String url = "jdbc:mysql://localhost:3306/ex";
    final static String driverClass = "org.h2.Driver";
    final static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    final static String user = "sa";
    final static String passwd = "sa";
    final static JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);
    
    @AfterAll
    public static void clean(){
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom(TBL_PERSON).where().anyColumnValues(set(PERSON_FIRST_NAME,"Tina"));
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
        insert.intoTable(TBL_PERSON).columnValues(asList(set(PERSON_FIRST_NAME,"Tina"), set(PERSON_LAST_NAME,"Turner"), set(PERSON_EMAIL,"tina@rediffmail.com"), set(PERSON_IS_ADULT, true)));
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