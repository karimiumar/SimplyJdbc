package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;

/**
 * Represents a database person table
 *
 * @author umar
 */
public class PersonTable extends Table {

    public static final String EX_SCHEMA = "ex";
    public static final Column<Integer> PERSON_ID = column("id");
    public static final Column<String> FIRST_NAME = column("firstname");
    public static final Column<String> LAST_NAME = column("lastname");
    public static final Column<String> EMAIL = column("email");
    public static final Column<Boolean> ADULT = column("adult");
    public static final Column<LocalDateTime> CREATED = column("created");
    public static final Column<LocalDateTime> UPDATED = column("updated");
    public static final Column<String> CITY = column("city");
    public static final Column<String> COUNTRY = column("country");
    public static final Column<Integer> AGE = column("age");
    public static final Table PERSON = table(EX_SCHEMA + ".person", PERSON_ID);

    public PersonTable(String tableName, Column<Integer> idColumn) {
        super(PERSON.getTableName(), PERSON_ID);
    }

    public static final RowMapper<Person> PERSON_ROW_MAPPER = (rs) -> {
        final Person personRow = new Person();
        personRow.setId(rs.getInt(PERSON_ID.getColumnName()));
        personRow.setFirstName(rs.getString(FIRST_NAME.getColumnName()));
        personRow.setLastName(rs.getString(LAST_NAME.getColumnName()));
        personRow.setEmail(rs.getString(EMAIL.getColumnName()));
        personRow.setAdult(rs.getBoolean(ADULT.getColumnName()));
        personRow.setCreated(rs.getTimestamp(CREATED.getColumnName()).toLocalDateTime());
        if (rs.getTimestamp(UPDATED.getColumnName()) != null) {
            personRow.setUpdated(rs.getTimestamp(UPDATED.getColumnName()).toLocalDateTime());
        }
        personRow.setCity(rs.getString(CITY.getColumnName()));
        personRow.setCountry(rs.getString(COUNTRY.getColumnName()));
        personRow.setAge(rs.getInt(AGE.getColumnName()));
        return personRow;
    };
}
