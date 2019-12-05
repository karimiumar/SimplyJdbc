package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Supplier {

    int id;
    String supplierName;
    String contactName;
    String supplierAddress;
    LocalDateTime created;
    LocalDateTime updated;
    List<Product> supplierOfProducts = new ArrayList<>();
    
    private Supplier() {
        
    }
    
    public static Supplier emptySupplier() {
        return new Supplier();
    }
    
    public Supplier(String supplierName, String contactName, String supplierAddress) {
        this.supplierName = supplierName;
        this.contactName = contactName;
        this.supplierAddress = supplierAddress;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.id;
        hash = 73 * hash + Objects.hashCode(this.supplierName);
        hash = 73 * hash + Objects.hashCode(this.contactName);
        return hash;
    }

    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", supplierName=" + supplierName + ", contactName=" + contactName + ", created=" + created + ", updated=" + updated + '}';
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
        final Supplier other = (Supplier) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.supplierName, other.supplierName)) {
            return false;
        }
        return Objects.equals(this.contactName, other.contactName);
    }

    public void add(Product product) {
        supplierOfProducts.add(product);
    }
    
    public List<Product> getSupplierOfProducts() {
        return supplierOfProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
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
}
