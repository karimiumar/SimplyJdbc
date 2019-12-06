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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteOpJdbcTest {

    final static String driverClass = "org.h2.Driver";
    final static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    final static String user = "sa";
    final static String passwd = "sa";
    final static JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);

    @BeforeEach
    public void prepare() {
        InsertOp insert = InsertOp.create();
        insert.intoTable(TBL_PERSON).columnValues(asList(set(PERSON_FIRST_NAME,"Tina"), set(PERSON_LAST_NAME,"Turner"), set(PERSON_EMAIL,"tina@rediffmail.com"), set(PERSON_IS_ADULT, true), set(PERSON_AGE, 32)));
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert.getSQL())) {
            insert.fill(ps).addBatch();
            insert = InsertOp.create().intoTable(TBL_PERSON).columnValues(asList(set(PERSON_FIRST_NAME,"Angelina"), set(PERSON_LAST_NAME,"Jolie"), set(PERSON_EMAIL,"angel@rediffmail.com"), set(PERSON_IS_ADULT, true), set(PERSON_AGE,27)));
            insert.fill(ps).addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   // @Test
    public void deleteWhereOrConditon(){
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom(TBL_PERSON).where().anyColumnValues(set(PERSON_FIRST_NAME,"Tina"), set(PERSON_FIRST_NAME,"Angelina"));
        try(Connection connection = util.getConnection();
            PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            int result = operation.fill(ps).executeUpdate();
            Assertions.assertEquals(2, result);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void deleteWhereConditon(){
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom(TBL_PERSON).where().columnEq(set(PERSON_FIRST_NAME,"Tina")).and().columnEq(set(PERSON_LAST_NAME,"Turner"));
        try(Connection connection = util.getConnection();
            PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            int result = operation.fill(ps).executeUpdate();
            Assertions.assertEquals(1, result);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
