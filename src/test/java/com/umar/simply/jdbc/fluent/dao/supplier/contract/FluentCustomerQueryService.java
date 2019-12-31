package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Customer;
import com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable;
import java.util.List;
import java.util.Optional;

public interface FluentCustomerQueryService {
    List<Customer> findTotalAmtOrderedByEachCustomer();
    Optional<Customer> findById(CustomerTable.Id id);
}
