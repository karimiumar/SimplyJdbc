package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.InsertOp;


import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Person.TblPerson.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Product.TblProduct.*;
import static java.util.Arrays.asList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertOpTest {

    public void insertIntoWithSelect() {
        String expected = "INSERT INTO Customer (FirstName, LastName, City, Country, Phone)\n" +
                "    SELECT LEFT(ContactName, CHARINDEX(' ',ContactName) - 1), \n" +
                "           SUBSTRING(ContactName, CHARINDEX(' ',ContactName) + 1, 100), \n" +
                "           City, Country, Phone\n" +
                "      FROM Supplier\n" +
                "     WHERE CompanyName = 'Bigfoot Breweries'";
    }

    @Test
    public void insertIntoProduct() {
        InsertOp sql = InsertOp.create();
        sql.intoTable(product).columns(asList(prd_name,suppid,unitprice,discontinued,cat_id));
        String expected = "INSERT INTO product(product_name,supplier_id,unit_price,is_discontinued,category_id)VALUES(?,?,?,?,?)";
        String result = sql.getSQL();
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void insertIntoPerson(){
        InsertOp sql = InsertOp.create();
        sql.intoTable(person).columns(asList(fname,lname,email,country,city,adult));
        String expected = "INSERT INTO person(firstname,lastname,email,country,city,adult)VALUES(?,?,?,?,?,?)";
        String result = sql.getSQL();
        Assertions.assertEquals(result,expected);
    }
}
