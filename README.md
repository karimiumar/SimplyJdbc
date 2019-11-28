# SimplyJdbc
Mimics JOOQ style of queries using JDBC. Its for learning purposes only ;)
See test examples on how to use it. You'll have to hand-code the domain classes and db-equivalent classes. 
Also, see Ceylon implementation of this library CeylonicJdbc


If you want to convert a SQL query that lists out all the customers whose orders are summed up and list the list is displayed in descending order of the total_amount then the SQL query written is:
```
SELECT * FROM ex.customer 
LEFT JOIN 
(
	SELECT customer_id, SUM(total_amount) AS TOTAL_AMOUNT  FROM ex.orders GROUP BY customer_id
) AS CUSTOMER_TOTALS  ON id=CUSTOMER_TOTALS.CUSTOMER_ID ORDER BY  SUM(total_amount) DESC
```
It results in the following result:

|CUSTOMER_TOTALS|first_name|last_name|
|---------------|:---------:|:--------:|
|6500.0	        |Ashley    |Stevens|
| 2200.0	|Jennifer|Aniston|
| 1100.0	|Brian|Adams|
| 700.0	        |Jennifer|Stevens|


The corresponding SimplyJDBC statement would be:
```
List<Customer> customerOrders = select().all().from(customer)
                .left().join()
                .beginComplex()
                .select().sum(totalAmount).as(TOTAL_AMOUNT).with(asList(orderCustomerId))
                .from(orders).groupBy(orderCustomerId)
                .endComplex()
                .as(CUSTOMER_TOTALS)
                .on().column(customerId).eq(CUSTOMER_TOTALS_CustomerId)
                .orderBy().column(CUSTOMER_TOTALS_TotalAmount).desc()
                .using(TotalAmtOrderedByEachCustomerMap).execute();
```
And the ```TotalAmtOrderedByEachCustomerMap ``` is a JDBC ResultSet map as:
```
public static RowMapper<Customer> TotalAmtOrderedByEachCustomerMap = (rs) -> {
            Customer customerRow = new Customer();
            customerRow.id = rs.getInt(customer_id_rsmd.getColumnName());
            customerRow.firstName = rs.getString(customer_fname_rsmd.getColumnName());
            customerRow.lastName = rs.getString(customer_lname_rsmd.getColumnName());
            customerRow.city = rs.getString(customer_city_rsmd.getColumnName());
            customerRow.country = rs.getString(customer_cuntry_rsmd.getColumnName());
            customerRow.created = rs.getTimestamp(customer_created_rsmd.getColumnName()).toLocalDateTime();
            if (rs.getTimestamp(customer_updated_rsmd.getColumnName()) != null) {
                customerRow.updated = rs.getTimestamp(customer_updated_rsmd.getColumnName()).toLocalDateTime();
            }
            customerRow.totalAmount = rs.getDouble(TOTAL_AMOUNT);
            return customerRow;
        };
```

Check the examples located under package ```com.umar.simply.jdbc.fluent.dao.supplier``` for details
