package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;
import java.util.Objects;

public class Supplier {

    int id;
    String supplierName;
    String contactName;
    String supplierAddress;
    LocalDateTime created;
    LocalDateTime updated;
    
    private Supplier() {
        
    }
    
    public Supplier(String supplierName, String contactName, String supplierAddress) {
        this.supplierName = supplierName;
        this.contactName = contactName;
        this.supplierAddress = supplierAddress;
    }
    
    public interface TblSupplier {

        Column<Integer> supplierId = column("id");
        Column<String> supplierName = column("supplier_name");
        Column<String> contactName = column("contact_name");
        Column<String> supplierAddr = column("supplier_address");
        Column<LocalDateTime> supplierCreatedOn = column("created");
        Column<LocalDateTime> supplierUpdatedOn = column("updated");
        Table supplier = Table.table("ex.supplier", supplierId);
        
        /**
         * If the returning SQL ResultSet consist of joins of two or more
         * tables then the given Mapping should be used by the RowMapper.map(ResultSet) as the ResultSetMetaData
         * only has information about actual table column names and all the aliases
         * created to are lost. 
         */
        Table supplier_rsmd = Table.table("supplier", supplierId);
        Column<Integer> s_id_rsmd = as("id", supplier_rsmd.getTableName());
        Column<String> s_supplierName_rsmd = as("supplier_name", supplier_rsmd.getTableName());
        Column<String> s_contactName_rsmd = as("contact_name", supplier_rsmd.getTableName());
        Column<String> s_supplierAddr_rsmd = as("supplier_address", supplier_rsmd.getTableName());
        Column<LocalDateTime> s_created_rsmd = as("created", supplier_rsmd.getTableName());
        Column<LocalDateTime> s_updated_rsmd = as("updated", supplier_rsmd.getTableName());
        
        Column<Integer> s1_id = as("id", "s1");
        Column<String> s1_supplierName = as("supplier_name", "s1");
        Column<String> s1_contactName = as("contact_name", "s1");
        Column<String> s1_supplierAddr = as("supplier_address", "s1");
        Column<LocalDateTime> s1_created = as("created", "s1");
        Column<LocalDateTime> s1_updated = as("updated", "s1");
        Table s1_supplier = Table.as("ex.supplier", "s1", supplierId);

        Column<Integer> s2_Id = as("id", "s2");
        Column<String> s2_supplierName = as("supplier_name", "s2");
        Column<String> s2_contactName = as("contact_name", "s2");
        Column<String> s2_suppplierAddr = as("supplier_address", "s2");
        Column<LocalDateTime> s2_created = as("created", "s2");
        Column<LocalDateTime> s2_updated = as("updated", "s2");
        Table s2_supplier = Table.as("ex.supplier", "s2", supplierId);
        
        RowMapper<Supplier> SUPPLIER_ROW_MAPPER = (rs) -> {
            final Supplier rowSupplier = new Supplier();
            rowSupplier.id = rs.getInt(supplierId.getColumnName());
            rowSupplier.supplierName = rs.getString(supplierName.getColumnName());
            rowSupplier.contactName = rs.getString(contactName.getColumnName());
            rowSupplier.supplierAddress = rs.getString(supplierAddr.getColumnName());
            rowSupplier.created = rs.getTimestamp(supplierCreatedOn.getColumnName()).toLocalDateTime();
            if(rs.getTimestamp(supplierUpdatedOn.getColumnName()) != null) {
                rowSupplier.updated = rs.getTimestamp(supplierUpdatedOn.getColumnName()).toLocalDateTime();
            }
            return rowSupplier;
        };
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
