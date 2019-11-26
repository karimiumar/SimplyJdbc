package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentOrderSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.Order.TblOrder.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;

public class OrderSaveService implements FluentOrderSaveService {

    private List<ColumnValue> values;
    
    @Override
    public OrderSaveService save(Order transientOrder) {
        values = asList(
                set(orderDate, transientOrder.orderDate)
                ,set(orderCustomerId, transientOrder.customerId)
                ,set(orderNo, transientOrder.orderNo)
                ,set(totalAmount, transientOrder.totalAmount)
                ,set(created, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Order execute() {
        SavePersistenceService<Order> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Order saved = sps.save(orders).withValues(values).using(ORDER_ROW_MAPPER).execute();
        return saved;
    }
    
}
