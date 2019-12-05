package com.umar.simply.jdbc.dml;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Person.TblPerson.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.EX_SCHEMA;
import static java.util.Arrays.asList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteOpJdbcTest {

    /*static String driverClass = "org.h2.Driver";
    static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    static String user = "sa";
    static String passwd = "sa";
    static JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);

    @BeforeEach
    public void prepare() {
        InsertOp insert = InsertOp.create();
        insert.intoTable(EX_SCHEMA +"." +person).columnValues(asList(set(fname,"Tina"), set(lname,"Turner"), set(email,"tina@rediffmail.com"), set(adult, true), set(age, 32)));
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert.getSQL())) {
            insert.fill(ps).addBatch();
            insert = InsertOp.create().intoTable(EX_SCHEMA +"." +person).columnValues(asList(set(fname,"Angelina"), set(lname,"Jolie"), set(email,"angel@rediffmail.com"), set(adult, true), set(age,27)));
            insert.fill(ps).addBatch();
            ps.executeBatch();
            //connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteWhereOrConditon(){
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom(EX_SCHEMA +"." +person).where().anyColumnValues(set(fname,"Tina"), set(fname,"Angelina"));
        try(Connection connection = util.getConnection();
            PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            int result = operation.fill(ps).executeUpdate();
            Assertions.assertEquals(2, result);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }*/
}
