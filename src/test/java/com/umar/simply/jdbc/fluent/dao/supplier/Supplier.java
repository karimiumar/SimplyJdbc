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
    
    public static Supplier emptySupplier() {
        return new Supplier();
    }
    
    public Supplier(String supplierName, String contactName, String supplierAddress) {
        this.supplierName = supplierName;
        this.contactName = contactName;
        this.supplierAddress = supplierAddress;
    }
    
    public interface TblSupplier {
        Column<Integer> tblSupplierId = column("id");
        Column<String> tblSupplierName = column("supplier_name");
        Column<String> tblContactName = column("supplier_contact");
        Column<String> tblSupplierAddr = column("supplier_address");
        Column<LocalDateTime> supplierCreatedOn = column("created");
        Column<LocalDateTime> supplierUpdatedOn = column("updated");
        Table tblSupplier = Table.table("ex.supplier", tblSupplierId);
        
        /**
         * If the returning SQL ResultSet consist of joins of two or more
         * tables then the given Mapping should be used by the RowMapper.map(ResultSet) as the ResultSetMetaData
         * only has information about actual table column names and all the aliases
         * created to are lost. 
         */
        Table supplier_rsmd = Table.table("supplier", tblSupplierId);
        Column<Integer> s_id_rsmd = as(supplier_rsmd.getTableName(),"id");
        Column<String> s_supplierName_rsmd = as(supplier_rsmd.getTableName(),"supplier_name");
        Column<String> s_contactName_rsmd = as(supplier_rsmd.getTableName(),"supplier_contact");
        Column<String> s_supplierAddr_rsmd = as(supplier_rsmd.getTableName(),"supplier_address");
        Column<LocalDateTime> s_created_rsmd = as(supplier_rsmd.getTableName(),"created");
        Column<LocalDateTime> s_updated_rsmd = as(supplier_rsmd.getTableName(),"updated");
        
        RowMapper<Supplier> SUPPLIER_ROW_MAPPER = (rs) -> {
            final Supplier rowSupplier = new Supplier();
            rowSupplier.id = rs.getInt(tblSupplierId.getColumnName());
            rowSupplier.supplierName = rs.getString(tblSupplierName.getColumnName());
            rowSupplier.contactName = rs.getString(tblContactName.getColumnName());
            rowSupplier.supplierAddress = rs.getString(tblSupplierAddr.getColumnName());
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
