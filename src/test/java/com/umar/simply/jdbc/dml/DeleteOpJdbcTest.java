package com.umar.simply.jdbc.dml;

import com.umar.simply.jdbc.ddl.CreateTablesInH2db;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.umar.simply.jdbc.meta.ColumnValue.*;
import static java.util.Arrays.asList;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteOpJdbcTest {

    @BeforeAll
    public static void setup() {
        CreateTablesInH2db.setup();
    }
    @BeforeEach
    public void prepare() {
        InsertOp insert = InsertOp.create();
        insert.INTO_TABLE(TBL_PERSON).VALUES(asList(set(PERSON_FIRST_NAME,"Tina"), set(PERSON_LAST_NAME,"Turner"), set(PERSON_EMAIL,"tina@rediffmail.com"), set(PERSON_IS_ADULT, true), set(PERSON_AGE, 32)));
        try (Connection connection = JdbcUtilService.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert.getSQL())) {
            insert.setParametersOfPreparedStatement(ps).addBatch();
            insert = InsertOp.create().INTO_TABLE(TBL_PERSON).VALUES(asList(set(PERSON_FIRST_NAME,"Angelina"), set(PERSON_LAST_NAME,"Jolie"), set(PERSON_EMAIL,"angel@rediffmail.com"), set(PERSON_IS_ADULT, true), set(PERSON_AGE,27)));
            insert.setParametersOfPreparedStatement(ps).addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteWhereOrConditon(){
        DeleteOp operation = DeleteOp.create();
        operation.DELETE_FROM(TBL_PERSON).WHERE().anyColumnValues(eq(PERSON_FIRST_NAME,"Tina"), eq(PERSON_FIRST_NAME,"Angelina"));
        try(Connection connection = JdbcUtilService.getConnection();
            PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            int result = operation.setParametersOfPreparedStatement(ps).executeUpdate();
            Assertions.assertEquals(3, result);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void deleteWhereConditon(){
        DeleteOp operation = DeleteOp.create();
        operation.DELETE_FROM(TBL_PERSON).WHERE().EQ(eq(PERSON_FIRST_NAME,"Tina")).AND().EQ(eq(PERSON_LAST_NAME,"Turner"));
        try(Connection connection = JdbcUtilService.getConnection();
            PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            int result = operation.setParametersOfPreparedStatement(ps).executeUpdate();
            Assertions.assertEquals(1, result);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
