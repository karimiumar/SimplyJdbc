package com.umar.simply.jdbc.fluent.dao.supplier.db.tables;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.fluent.dao.supplier.Customer;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;

/**
 * Represents database's CUSTOMER Table
 *
 * @author umar
 */
public class CustomerTable {
    
    /*
    CUSTOMER_TOTALS is an alias for a subquery as given:
    (SELECT customer_id, SUM(total_amount) AS TOTAL_AMOUNT  FROM ex.orders GROUP BY customer_id) AS CUSTOMER_TOTALS
     */
    public static final String CUSTOMER_TOTALS = "CUSTOMER_TOTALS";
    public static final String ID = "CUSTOMER_ID";
    public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    public static final Column<Integer> CUSTOMER_TOTALS_CUSTOMER_ID = Column.column(CUSTOMER_TOTALS + "." +ID);
    public static final Column<Double> CUSTOMER_TOTALS_TOTAL_AMOUNT = Column.column(CUSTOMER_TOTALS + "." +TOTAL_AMOUNT);

    public static final Column<Integer> CUSTOMER_ID = column("id");
    public static final Column<String> COUNTRY = column("country");
    public static final Column<String> CITY = column("city");
    public static final Column<String> FIRST_NAME = column("first_name");
    public static final Column<String> LAST_NAME = column("last_name");
    public static final Column<LocalDateTime> CREATED = column("created");
    public static final Column<LocalDateTime> UPDATED = column("updated");
    public static final Table CUSTOMERS = Table.table("customer", CUSTOMER_ID);

    public static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs) -> {
        Customer customerRow = Customer.emptyCustomer();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsmd.getColumnLabel(index);
            String tableAlias = rsmd.getTableName(index);
            if (columnName.equals(TOTAL_AMOUNT.toLowerCase())) {
                customerRow.setTotalAmount(rs.getDouble(TOTAL_AMOUNT.toLowerCase()));
            }
        }
        customerRow.setId(rs.getInt(CUSTOMER_ID.getColumnName().toLowerCase()));
        customerRow.setFirstName(rs.getString(FIRST_NAME.getColumnName().toLowerCase()));
        customerRow.setLastName(rs.getString(LAST_NAME.getColumnName().toLowerCase()));
        customerRow.setCity(rs.getString(CITY.getColumnName().toLowerCase()));
        customerRow.setCountry(rs.getString(COUNTRY.getColumnName().toLowerCase()));
        customerRow.setCreated(rs.getTimestamp(CREATED.getColumnName().toLowerCase()).toLocalDateTime());
        if (rs.getTimestamp(UPDATED.getColumnName().toLowerCase()) != null) {
            customerRow.setUpdated(rs.getTimestamp(UPDATED.getColumnName().toLowerCase()).toLocalDateTime());
        }
        return customerRow;
    };
}
