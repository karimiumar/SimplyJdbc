package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import static com.umar.simply.jdbc.fluent.dao.supplier.Supplier.TblSupplier.supplier;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentSupplierDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

public class SupplierDeleteService implements FluentSupplierDeleteService {

    DeletePersistenceService<Supplier> dps = new DeletePersistenceService<>(JdbcUtilService.getConnection());
    
    @Override
    public SupplierDeleteService delete() {
        dps.from(supplier);
        return this;
    }

    @Override
    public SupplierDeleteService where() {
        dps.where();
        return this;
    }

    @Override
    public SupplierDeleteService where(SelectOp op) {
        dps.where(op);
        return this;
    }

    @Override
    public SupplierDeleteService anyColumnValues(List<ColumnValue> columnValues) {
        dps.anyColumnValues(columnValues);
        return this;
    }

    @Override
    public void execute() {
        dps.execute();
    }
    
}