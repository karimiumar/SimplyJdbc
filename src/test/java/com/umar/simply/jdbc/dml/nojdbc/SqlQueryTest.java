package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.dml.operations.SelectOp;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.meta.Column.column;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.dml.operations.SelectOp.create;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import static java.util.Arrays.asList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SqlQueryTest {
    @Test
    public void selectDistinctColumn(){
        SelectOp select = create();
        select.SELECT()
                .DISTINCT(CUSTOMER_COUNTRY)
                .FROM(TBL_CUSTOMERS)
                .ORDERBY(CUSTOMER_COUNTRY)
                .ASC();
        String result = select.getSQL();
        String expected = "SELECT  DISTINCT country FROM customer ORDER BY country ASC";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selectCountDistinctColumn(){
        SelectOp sql = create();
        sql.SELECT().COUNT(create().DISTINCT("country")).FROM("customer");
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(DISTINCT country) FROM customer";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selectMySQLTop10() {
        SelectOp sql = create();
        sql.SELECT().column(asList(CUSTOMER_COUNTRY, CUSTOMER_CITY)).FROM(TBL_CUSTOMERS).AS("C1").LIMIT(10);
        String result = sql.getSQL();
        String expected = "SELECT country,city FROM customer AS C1  LIMIT ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue>params = sql.getValues();
        Assertions.assertTrue(params.size() == 1);
    }

    @Test
    public void selectMySQLOffset6To10() {
        SelectOp sql = create();
        sql.SELECT().column(asList(CUSTOMER_COUNTRY, CUSTOMER_CITY)).FROM(TBL_CUSTOMERS).LIMIT(5).OFFSET(5);
        String result = sql.getSQL();
        String expected = "SELECT country,city FROM customer LIMIT ? OFFSET ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue>params = sql.getValues();
        Assertions.assertTrue(params.size() == 2);
    }

    @Test
    public void selectAll(){
        SelectOp sql = create();
        sql.SELECT().all().FROM("customer c");
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer c";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void in() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_CUSTOMERS)
                .WHERE().column(asList(CUSTOMER_COUNTRY)).IN(asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        //"SELECT * FROM customer c1 WHERE country IN ('Germany','US')";
        String expected = "SELECT * FROM customer WHERE country IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void inUsingSchemaVars() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_CUSTOMERS)
                .WHERE().column(asList(CUSTOMER_COUNTRY))
                .IN(asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE country IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void whereInUsingExSchemaVars() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_CUSTOMERS).WHERE().column(CUSTOMER_COUNTRY).IN(asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE country IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

   @Test
    public void notIn() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_CUSTOMERS)
                .WHERE().column(CUSTOMER_COUNTRY).NOT().IN(
                asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE country NOT  IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notInSubQuery() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_CUSTOMERS)
                .WHERE().column(CUSTOMER_FIRST_NAME).NOT().IN(
                create().SELECT().column(PERSON_FIRST_NAME).AS("p1.firstname").FROM(TBL_PERSON).AS("p1"));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE first_name NOT  IN (SELECT firstname AS p1.firstname  FROM person AS p1)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void avg() {
        SelectOp sql = create();
        sql.SELECT().AVG(PRODUCT_UNIT_PRICE_COL).FROM(TBL_PRODUCT);
        String result = sql.getSQL();
        String expected = "SELECT  AVG(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }
    
    @Test
    public void max() {
        SelectOp sql = create();
        sql.SELECT().MAX(PRODUCT_UNIT_PRICE_COL).FROM(TBL_PRODUCT);
        String result = sql.getSQL();
        String expected = "SELECT  MAX(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void min() {
        SelectOp sql = create();
        sql.SELECT().MIN(column("price")).FROM("product");
        String result = sql.getSQL();
        String expected = "SELECT  MIN(price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void sum(){
        SelectOp sql = create();
        sql.SELECT().SUM(PRODUCT_UNIT_PRICE_COL).FROM(TBL_PRODUCT);
        String result = sql.getSQL();
        String expected = "SELECT  SUM(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void asTest() {
        SelectOp sql = create();
        sql.SELECT().column(PERSON_FIRST_NAME).AS("NAME").FROM(TBL_PERSON);
        String result = sql.getSQL();
        String expected = "SELECT firstname AS NAME  FROM person";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void likeStartingWith_a() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).LIKE("a%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void likeEndingWith_a() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).LIKE("%a");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameHaving_or() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).LIKE("%or%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameHaving_r_InSecondPos() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).LIKE("_r%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameStartingWith_a_AndAtleast3Chars() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).LIKE("a_%_%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameStartsWith_a_AndEndsWith_o() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).LIKE("a%o");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notStartWith_a() {
        SelectOp sql = create();
        sql.SELECT().all().FROM(TBL_PERSON).WHERE().column(PERSON_FIRST_NAME).NOT().LIKE("a%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname NOT  LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void betweenTextValues(){
        SelectOp sql = create().SELECT().all().FROM(TBL_PRODUCT).WHERE().column(PRODUCT_NAME_COL)
                .BETWEEN(asList(set("Carnarvon Tigers"),set("Mozzarella di Giovanni")));
        String result = sql.getSQL();
        //SELECT * FROM product WHERE product_name BETWEEN 'Carnarvon Tigers' AND 'Mozzarella di Giovanni'
        String expected = "SELECT * FROM product WHERE product_name BETWEEN ? AND ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

   @Test
    public void betweenWithIn(){
        SelectOp sql = create().SELECT().all().FROM(TBL_PRODUCT).WHERE(create().column(PRODUCT_UNIT_PRICE_COL)
                .BETWEEN(asList(set(10), set(20))))
                .AND().NOT().column(PRODUCT_CAT_ID_COL).IN(asList(set(1), set(2), set(3)));
        String result = sql.getSQL();
        //"SELECT * FROM product WHERE (unit_price BETWEEN '10' AND '20') AND  NOT category_id IN ('1','2','3')"
        String expected = "SELECT * FROM product WHERE (unit_price BETWEEN ? AND ?) AND  NOT category_id IN (?,?,?)";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(5, params.size());
    }

    @Test
    public void notBetween(){
        SelectOp sql = create().SELECT().all().FROM(TBL_PRODUCT)
                .WHERE().column(PRODUCT_UNIT_PRICE_COL).NOT().BETWEEN(asList(set(10),set(20)));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product WHERE unit_price NOT  BETWEEN ? AND ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
    }

    @Test
    public void isNull(){
        SelectOp sql = create().SELECT().all().FROM(TBL_PRODUCT)
                .WHERE().column(PRODUCT_NAME_COL).IS().values(set(null));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product WHERE product_name IS ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void orderBy(){
        SelectOp sql = create().SELECT(asList(PRODUCT_ID_COL, PRODUCT_NAME_COL, PRODUCT_UNIT_PRICE_COL))
                .FROM(TBL_PRODUCT).WHERE().column(PRODUCT_UNIT_PRICE_COL).EQ().values(set(20))
                .ORDERBY().column(PRODUCT_NAME_COL);
        String result = sql.getSQL();
        String expected = "SELECT id,product_name,unit_price FROM product WHERE unit_price=? ORDER BY product_name";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void groupBy(){
        SelectOp sql = create().SELECT().COUNT(CUSTOMER_ID)
                .with(CUSTOMER_COUNTRY)
                .FROM(TBL_CUSTOMERS).AS("c1").GROUPBY(CUSTOMER_COUNTRY);
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(id), country FROM customer AS c1  GROUP BY country";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(0, params.size());
    }
    
    @Test
    public void groupByOrderByDesc(){
        SelectOp sql = create()
                .SELECT().COUNT(column("id"))
                .with(column("country"))
                .FROM("customer")
                .GROUPBY(column("country"))
                .ORDERBY().COUNT(column("id")).DESC();
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(id), country FROM customer GROUP BY country ORDER BY  COUNT(id) DESC";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(0, params.size());
    }
    
    @Test
    public void subQuerySelectOrderByDesc(){
        SelectOp sql = create().SELECT().all().FROM(TBL_CUSTOMERS)
                .LEFT().JOIN(create()
                .SELECT().SUM(ORDER_TOTAL_AMT).AS("TOTAL_AMOUNT").with(asList(ORDER_CUSTOMERID))
                .FROM(TBL_ORDERS).GROUPBY(ORDER_CUSTOMERID))
                .AS("CUSTOMER_TOTALS")
                .ON().column(CUSTOMER_ID).EQ("CUSTOMER_TOTALS.CUSTOMER_ID")
                .ORDERBY().column("CUSTOMER_TOTALS.TOTAL_AMOUNT").DESC();
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer LEFT JOIN (SELECT  SUM(total_amount) AS TOTAL_AMOUNT ,customer_id FROM orders GROUP BY customer_id ) AS CUSTOMER_TOTALS  ON id=CUSTOMER_TOTALS.CUSTOMER_ID ORDER BY CUSTOMER_TOTALS.TOTAL_AMOUNT DESC";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void totalAmtOrderedForEachCustomer(){
        SelectOp sql = create().SELECT().SUM(ORDER_TOTAL_AMT).with(asList(CUSTOMER_FIRST_NAME,CUSTOMER_LAST_NAME))
                .FROM(TBL_ORDERS)
                .JOIN().TABLE(TBL_CUSTOMERS).AS("C")
                .ON().column(ORDER_CUSTOMERID).EQ().column(column("C.id"))
                .GROUPBY(asList(CUSTOMER_FIRST_NAME,CUSTOMER_LAST_NAME))
                .ORDERBY().SUM(ORDER_TOTAL_AMT)
                .DESC();
        String result = sql.getSQL();
        String expected = "SELECT  SUM(total_amount),first_name,last_name FROM orders JOIN customer AS C  ON customer_id=C.id GROUP BY first_name,last_name ORDER BY  SUM(total_amount) DESC";
        Assertions.assertEquals(result,expected);
    }

    /*
       Tests for HAVING CLAUSE
     */
    @Test
    public void havingCount(){
        SelectOp sql = create()
                .SELECT().COUNT(column("C.id")).with(column("C.country"))
                .FROM(TBL_CUSTOMERS).AS("C")
                .GROUPBY(column("C.country"))
                .HAVING().COUNT(column("C.id")).GT(set(10));
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(C.id), C.country FROM customer AS C  GROUP BY C.country HAVING  COUNT(C.id)>?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        ColumnValue cv0 = params.get(0);
        Assertions.assertTrue(10 == (Integer) cv0.getValue());
    }

    @Test
    public void listAllProductsStartingWithCha_or_Chan(){
        SelectOp sql = create().SELECT().column(asList(PRODUCT_ID_COL, PRODUCT_NAME_COL, PRODUCT_UNIT_PRICE_COL))
                .FROM(TBL_PRODUCT).WHERE().column(PRODUCT_NAME_COL).LIKE("Cha_").OR().column(PRODUCT_NAME_COL).LIKE("Chan_");
        String result = sql.getSQL();
        String expected = "SELECT id,product_name,unit_price FROM product WHERE product_name LIKE ? OR product_name LIKE ?";
        Assertions.assertEquals(result, expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
    }

    @Test
    public void selfJoin(){
        /**Match customers that are from the same city and country*/
        
        SelectOp sql = SelectOp.create().SELECT()
                .column(asList(column("c1.first_name"), column("c1.last_name"),
                        column("c2.first_name"), column("c2.last_name"),
                        column("c2.city"), column("c2.country")))
                .FROM(asList(Table.AS(TBL_CUSTOMERS.getTableName(), "c1"),Table.AS(TBL_CUSTOMERS.getTableName(), "c2")))
                .WHERE(column("c1.id")).NE(column("c2.id")).AND(column("c1.city")).EQ(column("c2.city"))
                .AND(column("c1.country")).EQ(column("c2.country")).ORDERBY(column("c1.country"));
        String result = sql.getSQL();
        String expected = "SELECT c1.first_name,c1.last_name,c2.first_name,c2.last_name,"
                +"c2.city,c2.country FROM customer c1,customer c2 WHERE c1.id<>c2.id AND c1.city=c2.city AND c1.country=c2.country ORDER BY c1.country";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void union(){

        SelectOp sql = SelectOp.create().SELECT().column("c.*").FROM("customer c")
                .UNION()
                .SELECT().column("s.*").FROM("customer c1");
        String result = sql.getSQL();
        String expected = "SELECT c.* FROM customer c UNION SELECT s.* FROM customer c1";
        Assertions.assertEquals(result,expected);
    }
    
    @Test
    public void intersectUsingInnerJoin(){
        /*Simulate INTERSECT operator IN MySQL USING DISTINCT AND INNER JOIN*/
        SelectOp sql = SelectOp.create().SELECT().DISTINCT().column("id").FROM("t1").INNER().JOIN().TABLE("t2").USING("id");
        String result = sql.getSQL();
        String expected = "SELECT  DISTINCT id FROM t1 INNER JOIN t2 USING(id)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void insersectUsingInAndSubQuery(){
        /*Simulate INTERSECT operator IN MySQL USING IN AND Subquery*/
        SelectOp sql = SelectOp.create().SELECT().DISTINCT().column("id").FROM("t1").WHERE()
                .column("id").IN(create().SELECT().column("id").FROM("t2"));
        String result = sql.getSQL();
        String expected = "SELECT  DISTINCT id FROM t1 WHERE id IN (SELECT id FROM t2)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void minus(){
        SelectOp sql = SelectOp.create().SELECT().column("id").FROM("t1").MINUS().SELECT().column("id").FROM("t2");
        String result = sql.getSQL();
        String expected = "SELECT id FROM t1 MINUS SELECT id FROM t2";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void minusSimulationUsingJoin(){
        SelectOp sql = SelectOp.create().SELECT().column("id").FROM("t1").LEFT().JOIN().TABLE("t2").USING("id")
                .WHERE().column("t2.id").IS().NULL();
        String result = sql.getSQL();
        String expected = "SELECT id FROM t1 LEFT JOIN t2 USING(id) WHERE t2.id IS  NULL";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void findDuplicates(){
        SelectOp sql = SelectOp.create().SELECT().COUNT(PERSON_EMAIL).with(PERSON_EMAIL)
                .FROM(TBL_PERSON).GROUPBY(PERSON_EMAIL).HAVING().COUNT(PERSON_EMAIL).GT().values(set(1));
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(email), email FROM person GROUP BY email HAVING  COUNT(email)>?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void crossJoin(){
        SelectOp sql = SelectOp.create().SELECT().all().FROM(asList(TBL_PRODUCT, TBL_ORDERS));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product,orders";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void testExists(){
        SelectOp sql = create().SELECT().all().FROM(TBL_SUPPLIER).AS("s1").WHERE()
                .EXISTS(
                        create().SELECT(PRODUCT_NAME_COL).AS("prd_name").FROM(TBL_PRODUCT)
                        .WHERE(PRODUCT_SUPPLIERID_COL).EQ(column("s1.id")).AND(PRODUCT_UNIT_PRICE_COL).LT().values(set(20))
                );
        String result = sql.getSQL();
        String expected = "SELECT * FROM supplier AS s1  WHERE  EXISTS (SELECT product_name AS prd_name  FROM product WHERE supplier_id=s1.id AND unit_price<? )";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }
    
    @Test
    public void testNotExists(){
        SelectOp sql = create().SELECT().all().FROM(TBL_SUPPLIER).AS("s1").WHERE().NOT()
                .EXISTS(
                        create().SELECT(PRODUCT_NAME_COL).FROM(TBL_PRODUCT).AS("prd1")
                        .WHERE(PRODUCT_SUPPLIERID_COL).EQ(column("s1.id")).AND(PRODUCT_UNIT_PRICE_COL).LT().values(set(20))
                );
        String result = sql.getSQL();
        String expected = "SELECT * FROM supplier AS s1  WHERE  NOT  EXISTS (SELECT product_name FROM product AS prd1  WHERE supplier_id=s1.id AND unit_price<? )";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }
    
    @Test()
    public void shouldFail() {
        Exception assertThrows = Assertions.assertThrows(RuntimeException.class, ()->{
            create().SELECT().all().FROM(TBL_SUPPLIER).WHERE().NOT()
                    .EXISTS(
                            create().SELECT(PRODUCT_NAME_COL).FROM(TBL_PRODUCT)
                                    .WHERE(PRODUCT_SUPPLIERID_COL).EQ(SUPPLIER_ID_COL).AND(PRODUCT_UNIT_PRICE_COL).LT().values(set(new Person()))
                    );
        },"Invalid type of value passed");
        Assertions.assertTrue(assertThrows.getMessage().contains("Invalid type of value passed"));
    }

}
