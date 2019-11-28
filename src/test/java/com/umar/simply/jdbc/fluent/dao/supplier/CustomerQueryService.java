package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.QueryService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentCustomerQueryService;
import java.sql.Connection;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.Customer.TblCustomer.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.Customer.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.Order.TblOrder.*;
import static java.util.Arrays.asList;

public class CustomerQueryService extends QueryService implements FluentCustomerQueryService {

    CustomerQueryService(Connection connection) {
        super(connection);
    }

    @Override
    public List<Customer> findTotalAmtOrderedByEachCustomer() {
        /*
         * The SQL query 
         *---------------------------------------------------------------------------------------
         * SELECT * FROM customer 
         * LEFT JOIN 
         * (
	 *      SELECT customer_id, SUM(amount) AS TOTAL_AMOUNT  FROM orders GROUP BY customer_id
         * ) AS CUSTOMER_TOTALS  
         * ON id = CUSTOMER_TOTALS.CUSTOMER_ID ORDER BY CUSTOMER_TOTALS.TOTAL_AMOUNT DESC
         * --------------------------------------------------------------------------------------
         */
        List<Customer> customerOrders = select().all().from(customer)
                .left().join(
                    SelectOp.create().select().sum(totalAmount).as(TOTAL_AMOUNT).with(asList(orderCustomerId))
                .from(orders).groupBy(orderCustomerId))
                .as(CUSTOMER_TOTALS)
                .on().column(customerId).eq(CUSTOMER_TOTALS_CUSTOMER_ID)
                .orderBy().column(CUSTOMER_TOTALS_TOTAL_AMOUNT).desc()
                .using(CUSTOMER_ROW_MAPPER).execute();
        return customerOrders;
    }

}

