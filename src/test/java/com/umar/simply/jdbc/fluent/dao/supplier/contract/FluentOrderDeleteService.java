package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.supplier.OrderDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

public interface FluentOrderDeleteService {
    OrderDeleteService delete();
    OrderDeleteService where();
    OrderDeleteService where(SelectOp op);
    OrderDeleteService anyColumnValues(List<ColumnValue<?>> columnValues);
    void execute();
}
