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
    
    private List<ColumnValue<?>> columnValues;
    
    @Override
    public CustomerSaveService save(Customer transientCustomer) {
        columnValues = asList(
                set(CUSTOMER_FIRST_NAME, transientCustomer.getFirstName())
                ,set(CUSTOMER_LAST_NAME, transientCustomer.getLastName())
                ,set(CUSTOMER_CITY, transientCustomer.getCity())
                ,set(CUSTOMER_COUNTRY, transientCustomer.getCountry())
                ,set(CUSTOMER_CREATED, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Customer execute() {
        SavePersistenceService<Customer> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        return sps.save(TBL_CUSTOMERS).withValues(columnValues).using(CUSTOMER_ROW_MAPPER).execute();
    }
    
}
