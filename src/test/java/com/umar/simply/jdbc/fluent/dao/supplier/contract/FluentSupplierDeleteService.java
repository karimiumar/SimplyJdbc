package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.supplier.SupplierDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

public interface FluentSupplierDeleteService {
    SupplierDeleteService delete();
    SupplierDeleteService where();
    SupplierDeleteService where(SelectFunction op);
    SupplierDeleteService anyColumnValues(List<ColumnValue<?>> columnValues);
    void execute();
}
