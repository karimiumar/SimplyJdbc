# SimplyJdbc
Mimics JOOQ style of queries using JDBC. Its for learning purposes only ;)
See test examples on how to use it. You'll have to hand-code the domain classes and db-equivalent classes. 
Also, see Ceylon implementation of this library CeylonicJdbc


If you want to convert a SQL query that lists out all the customers whose orders are summed up and list the list is displayed in descending order of the total_amount then the SQL query written is:
```
SELECT * FROM customer 
LEFT JOIN 
(
	SELECT customer_id, SUM(total_amount) AS TOTAL_AMOUNT  FROM orders GROUP BY customer_id
) AS CUSTOMER_TOTALS  ON id=CUSTOMER_TOTALS.CUSTOMER_ID ORDER BY  SUM(total_amount) DESC
```
It results in the following result:

|CUSTOMER_TOTALS|FIRST_NAME|LAST_NAME|
|---------------|:---------:|:--------:|
|6500.0	        |Ashley    |Stevens|
| 2200.0	|Jennifer|Aniston|
| 1100.0	|Brian|Adams|
| 700.0	        |Jennifer|Stevens|


The corresponding statement in SimplyJDBC would provide a List of Customer objects in descending order of the total amount of their orders.
```
List<Customer> customerOrders = select().all().from(TBL_CUSTOMERS)
                .left().join(SelectOp.create().select().sum(ORDER_TOTAL_AMT).as(TOTAL_AMOUNT).with(asList(ORDER_CUSTOMERID))
                .from(TBL_ORDERS).groupBy(ORDER_CUSTOMERID))
                .as(CUSTOMER_TOTALS)
                .on(CUSTOMER_ID).eq(CUSTOMER_TOTALS_CUSTOMER_ID)
                .orderBy(CUSTOMER_TOTALS_TOTAL_AMOUNT).desc()
                .using(CUSTOMER_ROW_MAPPER).execute();

```
The ```join(SelectOp op)``` statement takes a parameter of type ```SelectOp```. So in the above example, a new ```SELECT``` statement gets created that provides ```sum(totalAmount)```. The adjoining ```with(List<Column>)``` or ```with(Column)``` is used for group by clause to use columns with SQL aggregate functions like ```AVG, MAX, SUM```.
Check the examples located under package ```com.umar.simply.jdbc.dml.nojdbc``` and ```com.umar.simply.jdbc.fluent.dao.supplier``` for details

