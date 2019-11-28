package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductDeleteService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.Product.TblProduct.tblProduct;

public class ProductDeleteService implements FluentProductDeleteService {
    
    DeletePersistenceService<Product> dps = new DeletePersistenceService<>(JdbcUtilService.getConnection());

    @Override
    public ProductDeleteService delete() {
        dps.from(tblProduct);
        return this;
    }

    @Override
    public ProductDeleteService where() {
        dps.where();
        return this;
    }

    @Override
    public ProductDeleteService where(SelectOp op) {
        dps.where(op);
        return this;
    }

    @Override
    public ProductDeleteService anyColumnValues(List<ColumnValue> columnValues) {
        dps.anyColumnValues(columnValues);
        return this;
    }

    @Override
    public void execute() {
        dps.execute();
    }
    
}
