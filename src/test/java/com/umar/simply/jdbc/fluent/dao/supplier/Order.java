package com.umar.simply.jdbc.fluent.dao.supplier;

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
