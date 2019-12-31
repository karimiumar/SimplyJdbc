package com.umar.simply.jdbc.dml;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import com.umar.simply.jdbc.dml.operations.UpdateOp;
import com.umar.simply.jdbc.meta.ColumnValue;


import java.sql.*;

import static com.umar.simply.jdbc.meta.ColumnValue.*;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;

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

    final static String driverClass = "org.h2.Driver";
    final static String url = "jdbc:h2:./h2/db/ex;AUTO_SERVER=TRUE";
    final static String user = "sa";
    final static String passwd = "sa";
    final static JdbcUtil util = JdbcUtil.init(driverClass, url, user, passwd);
    
    private static Stream<Arguments> people() {
        return Stream.of(
            Arguments.of(set(PERSON_FIRST_NAME,"Tina"),set(PERSON_LAST_NAME,"Turner"),set(PERSON_EMAIL,"tina@rediffmail.com"),set(PERSON_IS_ADULT,true))
                ,Arguments.of(set(PERSON_FIRST_NAME,"Angelina"),set(PERSON_LAST_NAME, "Jolie"),set(PERSON_EMAIL,"angel@rediffmail.com"),set(PERSON_IS_ADULT, true))
                ,Arguments.of(set(PERSON_FIRST_NAME,"Angelina"),set(PERSON_LAST_NAME, "Adams"),set(PERSON_EMAIL,"angelina@rediffmail.com"),set(PERSON_IS_ADULT, true))
                ,Arguments.of(set(PERSON_FIRST_NAME,"Eva"),set(PERSON_LAST_NAME, "Jolie"),set(PERSON_EMAIL,"eva@rediffmail.com"),set(PERSON_IS_ADULT, false))
        );
    }

    @ParameterizedTest
    @MethodSource("people")
    @Order(1)
    public void insertPeople(ColumnValue<String> fname, ColumnValue<String> lname, ColumnValue<String> email, ColumnValue<Boolean> isAdult){
        InsertOp insert = InsertOp.create().INTO_TABLE(TBL_PERSON).VALUES(asList(fname,lname, email, isAdult));
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
        operation.DELETE_FROM(TBL_PERSON).WHERE().anyColumnValues(eq(PERSON_FIRST_NAME,"Tina"), eq(PERSON_FIRST_NAME,"Angelina"), eq(PERSON_FIRST_NAME,"Eva"), eq(PERSON_LAST_NAME,"Ali Karimi"));
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
        operation.TABLE(TBL_PERSON).SET(set(PERSON_FIRST_NAME,"Mohammad"),set(PERSON_LAST_NAME,"Ali Karimi")).WHERE().COLUMN_EQ(eq(PERSON_FIRST_NAME,"Tina"));
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
        UpdateOp update = new UpdateOp();
        update.TABLE(TBL_PERSON).SET(set(PERSON_FIRST_NAME,"Mohammad Umar"), set(PERSON_LAST_NAME,"Ali Karimi"), set(PERSON_EMAIL,"karimiumar@gmail.com"))
                .WHERE().COLUMN_EQ(eq(PERSON_FIRST_NAME,"Mohammad"), eq(PERSON_IS_ADULT,true), eq(PERSON_EMAIL,"tina@rediffmail.com"));
        try (Connection connection = util.getConnection();
             PreparedStatement ps = connection.prepareStatement(update.getSQL())) {
            int result = update.fill(ps).executeUpdate();
            Assertions.assertTrue(result > 0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
