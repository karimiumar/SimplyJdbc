package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.QueryService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentCustomerQueryService;
import java.sql.Connection;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;

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
        List<Customer> customerOrders = select().all().from(TBL_CUSTOMERS)
                .left().join(
                    SelectOp.create().SELECT().SUM(ORDER_TOTAL_AMT).AS(TOTAL_AMOUNT).with(ORDER_CUSTOMERID)
                    .FROM(TBL_ORDERS).GROUPBY(ORDER_CUSTOMERID)
                )
                .as(CUSTOMER_TOTALS)
                .on(CUSTOMER_ID).eq(CUSTOMER_TOTALS_CUSTOMER_ID)
                .orderBy(CUSTOMER_TOTALS_TOTAL_AMOUNT).desc()
                .using(CUSTOMER_ROW_MAPPER).execute();
        return customerOrders;
    }

}

