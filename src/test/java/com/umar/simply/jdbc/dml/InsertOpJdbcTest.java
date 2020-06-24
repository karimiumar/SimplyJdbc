/*
create TABLE PERSON
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

import com.umar.simply.jdbc.ddl.CreateTablesInH2db;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InsertOpJdbcTest  {

    @BeforeAll
    public static void setup() {
        System.out.println("Setting Up Test Database");
        CreateTablesInH2db.setup();
    }

    @AfterAll
    public static void clean(){
        DeleteOp operation = DeleteOp.create();
        operation.DELETE_FROM(TBL_PERSON).WHERE().anyColumnValues(set(PERSON_FIRST_NAME,"Tina"));
        try (Connection connection = JdbcUtilService.getConnection();
             PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            operation.fill(ps).executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInsertOp() {
        InsertOp insert = InsertOp.create();
        insert.INTO_TABLE(TBL_PERSON).VALUES(asList(set(PERSON_FIRST_NAME,"Tina"), set(PERSON_LAST_NAME,"Turner"), set(PERSON_EMAIL,"tina@rediffmail.com"), set(PERSON_IS_ADULT, true)));
        try (Connection connection = JdbcUtilService.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert.getSQL())) {
            int result = insert.fill(ps).executeUpdate();
            Assertions.assertTrue(result > 0);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}