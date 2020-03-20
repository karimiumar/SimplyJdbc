package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.UpdateOp;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateTest {

    @Test
    public void combineAndConditionTest(){
        UpdateOp update = new UpdateOp();
        update.TABLE("PERSON").SET(set(PERSON_FIRST_NAME,"Eva"))
                .WHERE().NOT().EQ(set(PERSON_ID,1))
                .AND(SelectOp.create().EQ(set(PERSON_IS_ADULT,true))
                        .OR().EQ(set(PERSON_EMAIL,"abc@abc.com")));
        String result = update.getSQL();
        String expected = "UPDATE PERSON SET firstname=? WHERE  NOT id=? AND (adult=? OR email=? )";
        System.out.println(update.getSQL());
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 4);
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void combineAndConditionWithValuesTest(){
        UpdateOp update = new UpdateOp();
        update.TABLE(TBL_PERSON).SET(set(PERSON_FIRST_NAME,"Eva")).WHERE().NOT().EQ(set(PERSON_ID,123))
                .AND(SelectOp.create().EQ(set(PERSON_IS_ADULT,false)).OR().EQ(set(PERSON_EMAIL,"tina@123.com")));
        String result = update.getSQL();
        String expected = "UPDATE person SET firstname=? WHERE  NOT id=? AND (adult=? OR email=? )";
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 4);
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void complexOrConditionTest() {
        UpdateOp update = new UpdateOp();
        update.TABLE(TBL_ORDERS).SET(set(ORDER_CUSTOMERID,44))
                .WHERE()
                .NOT(SelectOp.create()
                    .GE(set(ORDER_TOTAL_AMT, 5000.00))
                    .AND()
                    .LE(set(ORDER_TOTAL_AMT, 12000.00))
                );
        String result = update.getSQL();
        String expected = "UPDATE orders SET customer_id=? WHERE  NOT( total_amount>=? AND total_amount<=? )";
        System.out.println(result);
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 3);
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void complexConditionTest() {
        UpdateOp update = new UpdateOp();
        update.TABLE(TBL_PERSON).SET(set(PERSON_CITY, "Dusseldorf"))
                .WHERE()
                .NOT(SelectOp.create()
                .EQ(set(PERSON_COUNTRY, "Germany"))
                );
        String result = update.getSQL();
        String expected = "UPDATE person SET city=? WHERE  NOT( country=? )";
        System.out.println(result);
        Assertions.assertEquals(result, expected);
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 2);
        System.out.println(update.getSQL());
    }

}
