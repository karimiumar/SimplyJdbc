package com.umar.simply.jdbc.fluent.dao.supplier;

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
    public List<Customer> totalAmtOrderedByEachCustomerUsingInnerQuery() {

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
        return customerOrders;
    }

}

