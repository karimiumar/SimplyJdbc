package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Customer;
import java.util.List;

public interface FluentCustomerQueryService {
    List<Customer> totalAmtOrderedByEachCustomerUsingInnerQuery();
}
