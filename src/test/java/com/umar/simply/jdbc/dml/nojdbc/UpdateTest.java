package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.UpdateOp;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Person.TblPerson.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Order.TblOrder.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateTest {

    @Test
    public void combineAndConditionTest(){
        UpdateOp update = new UpdateOp();
        update.table("PERSON").setColumnValues(set(fname,"Eva")).where().not().columnEq(personId).and()
                .beginComplex().columnEq(adult).or().columnEq(email).endComplex();
        String result = update.getSQL();
        String expected = "UPDATE PERSON SET firstname=? WHERE  NOT id=? AND (adult=? OR email=?)";
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void combineAndConditionWithValuesTest(){
        UpdateOp update = new UpdateOp();
        update.table(person).setColumnValues(set(fname,"Eva")).where().not().columnValueEq(set(personId,123))
                .and()
                .beginComplex().columnValueEq(set(adult,false)).or().columnValueEq(set(email,"tina@123.com"))
                .endComplex();
        String result = update.getSQL();
        String expected = "UPDATE person SET firstname=? WHERE  NOT id=? AND (adult=? OR email=?)";
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void complexOrConditionTest() {
        UpdateOp update = new UpdateOp();
        update.table("ORDER").setColumnValues(set(customerId,44))
                .where()
                .not()
                .beginComplex()
                .geCol(totalAmount)
                .and()
                .leCol(totalAmount)
                .endComplex();
        String result = update.getSQL();
        String expected = "UPDATE ORDER SET customer_id=? WHERE  NOT (total_amount>=? AND total_amount<=?)";
        System.out.println(result);
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void complexConditionTest() {
        UpdateOp update = new UpdateOp();
        update.table("customer").setColumnValues(set(city, "Dusseldorf"))
                .where()
                .not()
                .beginComplex()
                .columnEq(country)
                .endComplex();
        String result = update.getSQL();
        String expected = "UPDATE customer SET city=? WHERE  NOT (country=?)";
        System.out.println(result);
        Assertions.assertEquals(result, expected);
    }

}
