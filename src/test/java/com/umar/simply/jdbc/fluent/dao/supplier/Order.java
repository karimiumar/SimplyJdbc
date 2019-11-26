package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    int id;
    LocalDateTime orderDate;
    int orderNo;
    int customerId;
    double totalAmount;
    LocalDateTime created;
    LocalDateTime updated;
    
    private Order() {
        
    }

    public Order(LocalDateTime orderDate, int orderNo, int customerId, double totalAmount) {
        this.orderDate = orderDate;
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }
        
    public static Order emptyOrder() {
        return new Order();
    }

    public interface TblOrder {

        Column<Integer> orderId = column("id");
        Column<LocalDateTime> orderDate = column("order_date");
        Column<Integer> orderNo = column("order_number");
        Column<Integer> orderCustomerId = column("customer_id");
        Column<Double> totalAmount = column("total_amount");
        Column<LocalDateTime> created = column("created");
        Column<LocalDateTime> updated = column("updated");
        Table orders = Table.table("ex.orders", orderId); //ORDER can't be a table identifier. Its a command type
        
        /**
         * If the returning SQL ResultSet consist of joins of two or more
         * tables then the given Mapping should be used by the RowMapper.map(ResultSet) as the ResultSetMetaData
         * only has information about actual table column names and all the aliases
         * created to are lost. 
         */
        Table orders_rsmd = Table.table("orders", orderId);
        Column<Integer> order_id_rsmd = as("id", orders_rsmd.getTableName());
        Column<LocalDateTime> order_orderDate_rsmd = as("order_date", orders_rsmd.getTableName());
        Column<Long> order_orderNo_rsmd = as("order_number", orders_rsmd.getTableName());
        Column<Integer> order_customerId_rsmd = as("customer_id", orders_rsmd.getTableName());
        Column<Double> order_totalAmount_rsmd = as("total_amount", orders_rsmd.getTableName());
        Column<LocalDateTime> order_created_rsmd = as("created", orders_rsmd.getTableName());
        Column<LocalDateTime> order_updated_rsmd = as("updated", orders_rsmd.getTableName());
        
        Column<Integer> o1_id = as("id", "o1");
        Column<LocalDateTime> o1_orderDate = as("order_date", "o1");
        Column<Long> o1_orderNo = as("order_number", "o1");
        Column<Integer> o1_customerId = as("customer_id", "o1");
        Column<Double> o1_totalAmount = as("total_amount", "o1");
        Column<LocalDateTime> o1_created = as("created", "o1");
        Column<LocalDateTime> o1_updated = as("updated", "o1");
        Table o1 = Table.as("ex.orders", "o1", orderId);

        Column<Integer> o2_id = as("id", "o2");
        Column<LocalDateTime> o2_orderDate = as("order_date", "o2");
        Column<Integer> o2_orderNo = as("order_number", "o2");
        Column<Integer> o2_customerId = as("customer_id", "o2");
        Column<Double> o2_totalAmount = as("total_amount", "o2");
        Column<LocalDateTime> o2_created = as("created", "o2");
        Column<LocalDateTime> o2_updated = as("updated", "o2");
        Table o2 = Table.as("ex.orders", "o2", orderId);
        
        RowMapper<Order> ORDER_ROW_MAPPER = (rs)-> {
            final Order rowOrder = new Order();
            rowOrder.id = rs.getInt(orderId.getColumnName());
            rowOrder.orderDate = rs.getTimestamp(orderDate.getColumnName()).toLocalDateTime();
            rowOrder.orderNo = rs.getInt(orderNo.getColumnName());
            rowOrder.customerId = rs.getInt(orderCustomerId.getColumnName());
            rowOrder.totalAmount = rs.getDouble(totalAmount.getColumnName());
            rowOrder.created = rs.getTimestamp(created.getColumnName()).toLocalDateTime();
            if(rs.getTimestamp(updated.getColumnName()) != null) {
                rowOrder.updated = rs.getTimestamp(updated.getColumnName()).toLocalDateTime();
            }
            return rowOrder;
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.orderDate);
        hash = 17 * hash + this.customerId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        return Objects.equals(this.orderDate, other.orderDate);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderDate=" + orderDate + ", orderNo=" + orderNo + ", customerId=" + customerId + ", totalAmount=" + totalAmount + ", created=" + created + ", updated=" + updated + '}';
    }
}
