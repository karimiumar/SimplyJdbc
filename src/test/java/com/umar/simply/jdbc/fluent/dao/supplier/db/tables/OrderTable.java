package com.umar.simply.jdbc.fluent.dao.supplier.db.tables;

import com.umar.simply.jdbc.fluent.dao.supplier.Order;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;
import com.umar.simply.jdbc.ResultSetMapper;

/**
 * Represents database's ORDERS Table
 *
 * @author umar
 */
public class OrderTable {

    public static final Column<Integer> ORDER_ID = column("id");
    public static final Column<LocalDateTime> ORDER_DATE = column("order_date");
    public static final Column<Integer> ORDER_NO = column("order_number");
    public static final Column<Integer> ORDER_CUSTOMERID = column("customer_id");
    public static final Column<Double> ORDER_TOTAL_AMT = column("total_amount");
    public static final Column<LocalDateTime> ORDER_CREATED = column("created");
    public static final Column<LocalDateTime> ORDER_UPDATED = column("updated");
    public static final Table TBL_ORDERS = Table.table("orders", ORDER_ID); 

    /*
     * If the returning SQL ResultSet consist of joins of two or more tables
     * then the given Mapping should be used by the ResultSetMapper.map(ResultSet) as
     * the ResultSetMetaData only has information about actual table column
     * names and all the aliases created to are lost.
     */
    public static final Table orders_rsmd = Table.table(TBL_ORDERS.getTableName(), ORDER_ID);
    public static final Column<Integer> order_id_rsmd = as(orders_rsmd.getTableName(), "id");
    public static final Column<LocalDateTime> order_orderDate_rsmd = as(orders_rsmd.getTableName(), "order_date");
    public static final Column<Long> order_orderNo_rsmd = as(orders_rsmd.getTableName(), "order_number");
    public static final Column<Integer> order_customerId_rsmd = as(orders_rsmd.getTableName(), "customer_id");
    public static final Column<Double> order_totalAmount_rsmd = as(orders_rsmd.getTableName(), "total_amount");
    public static final Column<LocalDateTime> order_created_rsmd = as(orders_rsmd.getTableName(), "created");
    public static final Column<LocalDateTime> order_updated_rsmd = as(orders_rsmd.getTableName(), "updated");

    public static final ResultSetMapper<Order> ORDER_ROW_MAPPER = (rs) -> {
        final Order rowOrder = Order.emptyOrder();
        rowOrder.setId(rs.getInt(ORDER_ID.getColumnName()));
        rowOrder.setOrderDate(rs.getTimestamp(ORDER_DATE.getColumnName()).toLocalDateTime());
        rowOrder.setOrderNo(rs.getInt(ORDER_NO.getColumnName()));
        rowOrder.setCustomerId(rs.getInt(ORDER_CUSTOMERID.getColumnName()));
        rowOrder.setTotalAmount(rs.getDouble(ORDER_TOTAL_AMT.getColumnName()));
        rowOrder.setCreated(rs.getTimestamp(ORDER_CREATED.getColumnName()).toLocalDateTime());
        if (rs.getTimestamp(ORDER_UPDATED.getColumnName()) != null) {
            rowOrder.setUpdated(rs.getTimestamp(ORDER_UPDATED.getColumnName()).toLocalDateTime());
        }
        return rowOrder;
    };
}
