package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentCustomerSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.fluent.dao.supplier.Customer.TblCustomer.*;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;

public class CustomerSaveService implements FluentCustomerSaveService {
    
    private List<ColumnValue> columnValues;
    
    @Override
    public CustomerSaveService save(Customer transientCustomer) {
        columnValues = asList(
                set(fname, transientCustomer.firstName)
                ,set(lname, transientCustomer.lastName)
                ,set(city, transientCustomer.city)
                ,set(country, transientCustomer.country)
                ,set(created, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Customer execute() {
        SavePersistenceService<Customer> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Customer saved = sps.save(customer).withValues(columnValues).using(CUSTOMER_ROW_MAPPER).execute();
        return saved;
    }
    
}
