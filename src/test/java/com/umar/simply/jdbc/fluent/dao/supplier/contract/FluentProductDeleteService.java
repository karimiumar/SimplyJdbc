package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.supplier.ProductDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

public interface FluentProductDeleteService {
    ProductDeleteService delete();
    ProductDeleteService where();
    ProductDeleteService where(SelectOp op);
    ProductDeleteService anyColumnValues(List<ColumnValue> columnValues);
    void execute();
}
