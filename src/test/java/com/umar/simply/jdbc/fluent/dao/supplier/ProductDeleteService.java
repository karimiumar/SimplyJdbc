package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.TBL_PRODUCT;

public class ProductDeleteService implements FluentProductDeleteService {
    
    DeletePersistenceService<Product> dps = new DeletePersistenceService<>(JdbcUtilService.getConnection());

    @Override
    public ProductDeleteService delete() {
        dps.from(TBL_PRODUCT);
        return this;
    }

    @Override
    public ProductDeleteService where() {
        dps.where();
        return this;
    }

    @Override
    public ProductDeleteService where(SelectFunction op) {
        dps.where(op);
        return this;
    }

    @Override
    public ProductDeleteService anyColumnValues(List<ColumnValue<?>> columnValues) {
        dps.anyColumnValues(columnValues);
        return this;
    }

    @Override
    public void execute() {
        dps.execute();
    }
    
}
