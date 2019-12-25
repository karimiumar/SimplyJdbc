package com.umar.simply.jdbc.fluent.dao.supplier.db.tables;

import com.umar.simply.jdbc.fluent.dao.supplier.Supplier;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.column;
import static com.umar.simply.jdbc.meta.Column.as;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;
import com.umar.simply.jdbc.ResultSetMapper;
import static com.umar.simply.jdbc.meta.Column.as;

/**
 * Represents database's SUPPLIER table
 *
 * @author umar
 */
public class SupplierTable {

    public static final Column<Integer> SUPPLIER_ID_COL = column("id");
    public static final Column<String> SUPPLIER_NAME_COL = column("supplier_name");
    public static final Column<String> SUPPLIER_CONTACT_COL = column("supplier_contact");
    public static final Column<String> SUPPLIER_ADDR_COL = column("supplier_address");
    public static final Column<LocalDateTime> SUPPLIER_CREATED_COL = column("created");
    public static final Column<LocalDateTime> SUPPLIER_UPDATED_COL = column("updated");
    public static final Table TBL_SUPPLIER = Table.table("supplier", SUPPLIER_ID_COL);

    /**
     * If the returning SQL ResultSet consist of joins of two or more tables
 then the given Mapping should be used by the ResultSetMapper.map(ResultSet) as
 the ResultSetMetaData only has information about actual table column
 names and all the aliases created to are lost.
     */
    public static final Table supplier_rsmd = Table.table("supplier", SUPPLIER_ID_COL);
    public static final Column<Integer> s_id_rsmd = as(supplier_rsmd.getTableName(), "id");
    public static final Column<String> s_supplierName_rsmd = as(supplier_rsmd.getTableName(), "supplier_name");
    public static final Column<String> s_contactName_rsmd = as(supplier_rsmd.getTableName(), "supplier_contact");
    public static final Column<String> s_supplierAddr_rsmd = as(supplier_rsmd.getTableName(), "supplier_address");
    public static final Column<LocalDateTime> s_created_rsmd = as(supplier_rsmd.getTableName(), "created");
    public static final Column<LocalDateTime> s_updated_rsmd = as(supplier_rsmd.getTableName(), "updated");

    public static final ResultSetMapper<Supplier> SUPPLIER_ROW_MAPPER = (rs) -> {
        final Supplier rowSupplier = Supplier.emptySupplier();
        rowSupplier.setId(rs.getInt(SUPPLIER_ID_COL.getColumnName()));
        rowSupplier.setSupplierName(rs.getString(SUPPLIER_NAME_COL.getColumnName()));
        rowSupplier.setContactName(rs.getString(SUPPLIER_CONTACT_COL.getColumnName()));
        rowSupplier.setSupplierAddress(rs.getString(SUPPLIER_ADDR_COL.getColumnName()));
        rowSupplier.setCreated(rs.getTimestamp(SUPPLIER_CREATED_COL.getColumnName()).toLocalDateTime());
        if (rs.getTimestamp(SUPPLIER_UPDATED_COL.getColumnName()) != null) {
            rowSupplier.setUpdated(rs.getTimestamp(SUPPLIER_UPDATED_COL.getColumnName()).toLocalDateTime());
        }
        return rowSupplier;
    };
}
