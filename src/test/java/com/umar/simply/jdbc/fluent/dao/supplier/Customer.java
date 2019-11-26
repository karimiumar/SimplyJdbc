package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
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
    static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    static final Column<Integer> CUSTOMER_TOTALS_CustomerId = Column.column(CUSTOMER_TOTALS + ".CUSTOMER_ID");
    static final Column<Double> CUSTOMER_TOTALS_TotalAmount = Column.column(CUSTOMER_TOTALS +"."+TOTAL_AMOUNT);
    
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

        /**
         * If the returning SQL ResultSet consist of joins of two or more tables
         * then the given Mapping should be used by the RowMapper.map(ResultSet)
         * as the ResultSetMetaData only has information about actual table
         * column names and all the aliases created to are lost.
         */
        Table customer_rsmd = Table.table("customer", customerId);
        Column<Integer> customer_id_rsmd = as("id", customer_rsmd.getTableName());
        Column<String> customer_cuntry_rsmd = as("country", customer_rsmd.getTableName());
        Column<String> customer_city_rsmd = as("city", customer_rsmd.getTableName());
        Column<String> customer_fname_rsmd = as("first_name", customer_rsmd.getTableName());
        Column<String> customer_lname_rsmd = as("last_name", customer_rsmd.getTableName());
        Column<LocalDateTime> customer_created_rsmd = as("created", customer_rsmd.getTableName());
        Column<LocalDateTime> customer_updated_rsmd = as("updated", customer_rsmd.getTableName());

        Column<Integer> c1_id = as("id", "c1");
        Column<String> c1_cuntry = as("country", "c1");
        Column<String> c1_city = as("city", "c1");
        Column<String> c1_fname = as("first_name", "c1");
        Column<String> c1_lname = as("last_name", "c1");
        Column<LocalDateTime> c1_created = as("created", "c1");
        Column<LocalDateTime> c1_updated = as("updated", "c1");
        Table c1 = Table.as("ex.customer", "c1", customerId);

        Column<Integer> c2_id = as("id", "c2");
        Column<String> c2_cuntry = as("country", "c2");
        Column<String> c2_city = as("city", "c2");
        Column<String> c2_fname = as("first_name", "c2");
        Column<String> c2_lname = as("last_name", "c2");
        Column<LocalDateTime> c2_created = as("created", "c2");
        Column<LocalDateTime> c2_updated = as("updated", "c2");
        Table c2 = Table.as("ex.customer", "c2", customerId);

        RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs) -> {
            Customer customerRow = new Customer();
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

        public static RowMapper<Customer> TotalAmtOrderedByEachCustomerMap = (rs) -> {
            Customer customerRow = new Customer();
            customerRow.id = rs.getInt(customer_id_rsmd.getColumnName());
            customerRow.firstName = rs.getString(customer_fname_rsmd.getColumnName());
            customerRow.lastName = rs.getString(customer_lname_rsmd.getColumnName());
            customerRow.city = rs.getString(customer_city_rsmd.getColumnName());
            customerRow.country = rs.getString(customer_cuntry_rsmd.getColumnName());
            customerRow.created = rs.getTimestamp(customer_created_rsmd.getColumnName()).toLocalDateTime();
            if (rs.getTimestamp(customer_updated_rsmd.getColumnName()) != null) {
                customerRow.updated = rs.getTimestamp(customer_updated_rsmd.getColumnName()).toLocalDateTime();
            }
            customerRow.totalAmount = rs.getDouble(TOTAL_AMOUNT);
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
