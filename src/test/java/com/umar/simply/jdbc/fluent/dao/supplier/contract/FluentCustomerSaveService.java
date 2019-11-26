package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Customer;
import com.umar.simply.jdbc.fluent.dao.supplier.CustomerSaveService;

public interface FluentCustomerSaveService {
    CustomerSaveService save(Customer customer);
    Customer execute();
}
