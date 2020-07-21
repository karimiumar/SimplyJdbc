package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentOrderDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;

public class OrderDeleteService implements FluentOrderDeleteService {
    
    DeletePersistenceService<Order> dps = new DeletePersistenceService<>(JdbcUtilService.getConnection());

    @Override
    public OrderDeleteService delete() {
        dps.from(TBL_ORDERS);
        return this;
    }

    @Override
    public OrderDeleteService where() {
        dps.where();
        return this;
    }

    @Override
    public OrderDeleteService where(SelectFunction op) {
        dps.where(op);
        return this;
    }

    @Override
    public OrderDeleteService anyColumnValues(List<ColumnValue<?>> columnValues) {
        dps.anyColumnValues(columnValues);
        return this;
    }

    @Override
    public void execute() {
        dps.execute();
    }
    
}
