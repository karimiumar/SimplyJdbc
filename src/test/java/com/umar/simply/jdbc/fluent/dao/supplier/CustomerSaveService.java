package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentCustomerSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;
import java.time.LocalDateTime;

import static java.util.Arrays.asList;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;


public class CustomerSaveService implements FluentCustomerSaveService {
    
    private List<ColumnValue> columnValues;
    
    @Override
    public CustomerSaveService save(Customer transientCustomer) {
        columnValues = asList(
                set(FIRST_NAME, transientCustomer.getFirstName())
                ,set(LAST_NAME, transientCustomer.getLastName())
                ,set(CITY, transientCustomer.getCity())
                ,set(COUNTRY, transientCustomer.getCountry())
                ,set(CREATED, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Customer execute() {
        SavePersistenceService<Customer> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Customer saved = sps.save(CUSTOMERS).withValues(columnValues).using(CUSTOMER_ROW_MAPPER).execute();
        return saved;
    }
    
}
