package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.supplier.CustomerDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

public interface FluentCustomerDeleteService {
    CustomerDeleteService delete();
    CustomerDeleteService where();
    CustomerDeleteService where(SelectFunction op);
    CustomerDeleteService anyColumnValues(List<ColumnValue<?>> columnValues);
    void execute();
}
