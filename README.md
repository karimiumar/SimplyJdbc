# SimplyJdbc
Mimics JOOQ style of queries using JDBC. Its for learning purposes only ;)
See test examples on how to use it. You'll have to hand-code the domain classes and db-equivalent classes. 


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
List<Customer> customerOrders = SELECT().ALL().FROM(TBL_CUSTOMERS)
                .LEFT().JOIN(
                    SelectOp.create().SELECT().SUM(ORDER_TOTAL_AMT).AS(TOTAL_AMOUNT).GROUP_WITH(ORDER_CUSTOMERID)
                    .FROM(TBL_ORDERS).GROUPBY(ORDER_CUSTOMERID)
                )
                .AS(CUSTOMER_TOTALS)
                .ON(CUSTOMER_ID).EQ(CUSTOMER_TOTALS_CUSTOMER_ID)
                .ORDER_BY(CUSTOMER_TOTALS_TOTAL_AMOUNT).DESC()
                .using(CUSTOMER_ROW_MAPPER).execute();

```
The ```JOIN(SelectOp op)``` statement takes a parameter of type ```SelectOp```. So in the above example, a new ```SELECT``` statement gets created that provides ```SUM(ORDER_TOTAL_AMT)```. The adjoining ```GROUP_WITH(List<Column>)``` or ```GROUP_WITH(Column)``` is used for group by clause to use columns with SQL aggregate functions like ```AVG, MAX, SUM```.
Check the examples located under package ```com.umar.simply.jdbc.dml.nojdbc``` and ```com.umar.simply.jdbc.fluent.dao.supplier``` for details

