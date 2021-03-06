package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentCustomerDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;
import java.util.List;

public class CustomerDeleteService implements FluentCustomerDeleteService {

    DeletePersistenceService<Customer> dps = new DeletePersistenceService<>(JdbcUtilService.getConnection());
    
    @Override
    public CustomerDeleteService delete() {
        dps.from(TBL_CUSTOMERS);
        return this;
    }

    @Override
    public CustomerDeleteService where() {
        dps.where();
        return this;
    }

    @Override
    public CustomerDeleteService where(SelectFunction op) {
        dps.where(op);
        return this;
    }

    @Override
    public CustomerDeleteService anyColumnValues(List<ColumnValue<?>> columnValues) {
        dps.anyColumnValues(columnValues);
        return this;
    }

    @Override
    public void execute() {
        dps.execute();
    }
    
}
