package com.umar.simply.jdbc.dml;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import com.umar.simply.jdbc.dml.operations.UpdateOp;
import com.umar.simply.jdbc.meta.ColumnValue;


import java.sql.*;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import com.umar.simply.jdbc.sample.schema.metadata.ExSchema;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Person.TblPerson.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.EX_SCHEMA;
import static java.util.Arrays.asList;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestMethodOrder(OrderAnnotation.class)
public class UpdateOpJdbcTest  {

    static String driverClass = "org.h2.Driver";
    static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    static String user = "sa";
    static String passwd = "sa";
    static JdbcUtil util = JdbcUtil.init(driverClass, url, user, passwd);
    
    private static Stream<Arguments> people() {
        return Stream.of(
            Arguments.of(set(fname,"Tina"),set(lname,"Turner"),set(email,"tina@rediffmail.com"),set(adult,true))
                ,Arguments.of(set(fname,"Angelina"),set(lname, "Jolie"),set(email,"angel@rediffmail.com"),set(adult, true))
                ,Arguments.of(set(fname,"Angelina"),set(lname, "Adams"),set(email,"angelina@rediffmail.com"),set(adult, true))
                ,Arguments.of(set(fname,"Eva"),set(lname, "Jolie"),set(email,"eva@rediffmail.com"),set(adult, false))
        );
    }

    @ParameterizedTest
    @MethodSource("people")
    @Order(1)
    public void insertPeople(ColumnValue<String> fname, ColumnValue<String> lname, ColumnValue<String> email, ColumnValue<Boolean> isAdult){
        InsertOp insert = InsertOp.create().intoTable(EX_SCHEMA +"." +person).columnValues(asList(fname,lname, email, isAdult));
        try (Connection connection = util.getConnection();
            PreparedStatement ps = connection.prepareStatement(insert.getSQL())) {
            insert.fill(ps).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void clean() {
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom(EX_SCHEMA +"." +person).where().anyColumnValues(set(fname,"Tina"), set(fname,"Angelina"), set(fname,"Eva"), set(lname,"Ali Karimi"));
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            operation.fill(ps).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void updateWhere() {
        UpdateOp operation = new UpdateOp();
        operation.table(EX_SCHEMA +"." +person).setColumnValues(set(fname,"Mohammad"),set(lname,"Ali Karimi")).where().columnValueEq(set(fname,"Tina"));
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(operation.getSQL(), Statement.RETURN_GENERATED_KEYS)) {
            int result = operation.fill(ps).executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()) {
                    System.out.println("Key->" + rs.getInt(1));
                }else{
                    System.out.println("No key generated");
                }
            }
            Assertions.assertTrue(result > 0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @Order(3)
    public void updateWhereAnd() {
        UpdateOp operation = new UpdateOp();
        operation.table(EX_SCHEMA +"." +person).setColumnValues(set(fname,"Mohammad Umar"), set(lname,"Ali Karimi"), set(email,"karimiumar@gmail.com"))
                .where().columnValueEq(set(fname,"Mohammad"), set(adult,true), set(email,"tina@rediffmail.com"));
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(operation.getSQL())) {
            int result = operation.fill(ps).executeUpdate();
            Assertions.assertTrue(result > 0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
