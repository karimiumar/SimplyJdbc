package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.QueryService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentCustomerQueryService;
import java.sql.Connection;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
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
        List<Customer> customerOrders = select().all().from(CUSTOMERS)
                .left().join(
                    SelectOp.create().select().sum(ORDER_TOTAL_AMT).as(TOTAL_AMOUNT).with(asList(ORDER_CUSTOMERID))
                .from(TBL_ORDERS).groupBy(ORDER_CUSTOMERID))
                .as(CUSTOMER_TOTALS)
                .on(CUSTOMER_ID).eq(CUSTOMER_TOTALS_CUSTOMER_ID)
                .orderBy().column(CUSTOMER_TOTALS_TOTAL_AMOUNT).desc()
                .using(CUSTOMER_ROW_MAPPER).execute();
        String sql = select().all().from(CUSTOMERS)
                .left().join(
                    SelectOp.create().select().sum(ORDER_TOTAL_AMT).as(TOTAL_AMOUNT).with(asList(ORDER_CUSTOMERID))
                .from(TBL_ORDERS).groupBy(ORDER_CUSTOMERID))
                .as(CUSTOMER_TOTALS)
                .on(CUSTOMER_ID).eq(CUSTOMER_TOTALS_CUSTOMER_ID)
                .orderBy().column(CUSTOMER_TOTALS_TOTAL_AMOUNT).desc().getSQL().getSQL();
        System.out.println(sql);
        return customerOrders;
    }

}

