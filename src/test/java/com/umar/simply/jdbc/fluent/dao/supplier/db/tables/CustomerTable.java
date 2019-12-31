package com.umar.simply.jdbc.fluent.dao.supplier.db.tables;

import com.umar.simply.jdbc.fluent.dao.supplier.Customer;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import com.umar.simply.jdbc.ResultSetMapper;
import com.umar.simply.jdbc.meta.ColumnValue;

/**
 * Represents database's CUSTOMER Table
 *
 * @author umar
 */
public class CustomerTable {
    
    public static Id customerId() {
        return new Id("id");
    }
    
    public static Id setId(int val) {
        return new Id(val);
    }
    
    public static final class Id extends ColumnValue<Integer> {
        protected Id(String columnName) {
            super(column(columnName), 0);
        }
        
        protected Id(int val) {
            super(column("id"), val);
        }
    }
    
    /*
    CUSTOMER_TOTALS is an alias for a subquery AS given:
    (SELECT customer_id, SUM(total_amount) AS TOTAL_AMOUNT  FROM ex.orders GROUP BY customer_id) AS CUSTOMER_TOTALS
     */
    public static final String CUSTOMER_TOTALS = "CUSTOMER_TOTALS";
    public static final String ID = "CUSTOMER_ID";
    public static final String TOTAL_AMOUNT = "total_amount";
    public static final Column<Integer> CUSTOMER_TOTALS_CUSTOMER_ID = Column.column(CUSTOMER_TOTALS + "." +ID);
    public static final Column<Double> CUSTOMER_TOTALS_TOTAL_AMOUNT = Column.column(CUSTOMER_TOTALS + "." +TOTAL_AMOUNT);

    public static final Column<Integer> CUSTOMER_ID = customerId().getColumnName();
    public static final Column<String> CUSTOMER_COUNTRY = column("country");
    public static final Column<String> CUSTOMER_CITY = column("city");
    public static final Column<String> CUSTOMER_FIRST_NAME = column("first_name");
    public static final Column<String> CUSTOMER_LAST_NAME = column("last_name");
    public static final Column<LocalDateTime> CUSTOMER_CREATED = column("created");
    public static final Column<LocalDateTime> CUSTOMER_UPDATED = column("updated");
    public static final Table TBL_CUSTOMERS = Table.table("customer", CUSTOMER_ID);

    public static final ResultSetMapper<Customer> CUSTOMER_ROW_MAPPER = (rs) -> {
        Customer customerRow = Customer.emptyCustomer();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsmd.getColumnLabel(index);
            String tableAlias = rsmd.getTableName(index);
            if (columnName.toLowerCase().equals(TOTAL_AMOUNT.toLowerCase())) {
                customerRow.setTotalAmount(rs.getDouble(TOTAL_AMOUNT.toLowerCase()));
            }
        }
        customerRow.setId(rs.getInt(CUSTOMER_ID.getColumnName()));
        customerRow.setFirstName(rs.getString(CUSTOMER_FIRST_NAME.getColumnName()));
        customerRow.setLastName(rs.getString(CUSTOMER_LAST_NAME.getColumnName()));
        customerRow.setCity(rs.getString(CUSTOMER_CITY.getColumnName()));
        customerRow.setCountry(rs.getString(CUSTOMER_COUNTRY.getColumnName()));
        customerRow.setCreated(rs.getTimestamp(CUSTOMER_CREATED.getColumnName()).toLocalDateTime());
        if (rs.getTimestamp(CUSTOMER_UPDATED.getColumnName()) != null) {
            customerRow.setUpdated(rs.getTimestamp(CUSTOMER_UPDATED.getColumnName()).toLocalDateTime());
        }
        return customerRow;
    };
}
