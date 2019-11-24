package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Supplier;
import com.umar.simply.jdbc.fluent.dao.supplier.SupplierSaveService;

public interface FluentSupplierSaveService {
    SupplierSaveService save(Supplier supplier);
    Supplier execute();
}
