package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Order;
import com.umar.simply.jdbc.fluent.dao.supplier.OrderSaveService;

public interface FluentOrderSaveService {
    OrderSaveService save(Order order);
    Order execute();
}
