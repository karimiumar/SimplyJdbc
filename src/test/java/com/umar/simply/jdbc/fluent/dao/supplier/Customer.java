package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.util.Objects;

public class Customer {

    int id;
    String firstName;
    String lastName;
    String country;
    String city;
    transient double totalAmount; //this is not a database column but is required for joining with orders table.
    LocalDateTime created;
    LocalDateTime updated;

    /*
    CUSTOMER_TOTALS is an alias for a subquery as given:
    (SELECT customer_id, SUM(total_amount) AS TOTAL_AMOUNT  FROM ex.orders GROUP BY customer_id) AS CUSTOMER_TOTALS
     */
    static final String CUSTOMER_TOTALS = "CUSTOMER_TOTALS";
    static final String CUSTOMER_ID = "CUSTOMER_ID";
    static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    static final Column<Integer> CUSTOMER_TOTALS_CUSTOMER_ID = Column.column(CUSTOMER_TOTALS + "." +CUSTOMER_ID);
    static final Column<Double> CUSTOMER_TOTALS_TOTAL_AMOUNT = Column.column(CUSTOMER_TOTALS + "." +TOTAL_AMOUNT);
    
    private Customer() {

    }

    public Customer(String firstName, String lastName, String country, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
    }

    public static Customer emptyCustomer() {
        return new Customer();
    }

    public interface TblCustomer {

        Column<Integer> customerId = column("id");
        Column<String> country = column("country");
        Column<String> city = column("city");
        Column<String> fname = column("first_name");
        Column<String> lname = column("last_name");
        Column<LocalDateTime> created = column("created");
        Column<LocalDateTime> updated = column("updated");
        Table customer = Table.table("ex.customer", customerId);
        
        RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs) -> {
            Customer customerRow = new Customer();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int index = 1; index <= columnCount; index++) {
                String columnName = rsmd.getColumnLabel(index);
                String tableAlias = rsmd.getTableName(index);
                if(columnName.equals(TOTAL_AMOUNT)) {
                    customerRow.totalAmount = rs.getDouble(TOTAL_AMOUNT);
                }
            }
            customerRow.id = rs.getInt(customerId.getColumnName());
            customerRow.firstName = rs.getString(fname.getColumnName());
            customerRow.lastName = rs.getString(lname.getColumnName());
            customerRow.city = rs.getString(city.getColumnName());
            customerRow.country = rs.getString(country.getColumnName());
            customerRow.created = rs.getTimestamp(created.getColumnName()).toLocalDateTime();
            if (rs.getTimestamp(updated.getColumnName()) != null) {
                customerRow.updated = rs.getTimestamp(updated.getColumnName()).toLocalDateTime();
            }
            return customerRow;
        };
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.firstName);
        hash = 53 * hash + Objects.hashCode(this.lastName);
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
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        return Objects.equals(this.lastName, other.lastName);
    }
    
    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", country=" + country + ", city=" + city + ", totalAmount=" + totalAmount + ", created=" + created + ", updated=" + updated + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
