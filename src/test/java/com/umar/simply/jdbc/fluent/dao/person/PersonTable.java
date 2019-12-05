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

    public static final Column<Integer> PERSON_ID = column("id");
    public static final Column<String> PERSON_FIRST_NAME = column("firstname");
    public static final Column<String> PERSON_LAST_NAME = column("lastname");
    public static final Column<String> PERSON_EMAIL = column("email");
    public static final Column<Boolean> PERSON_IS_ADULT = column("adult");
    public static final Column<LocalDateTime> PERSON_CREATED = column("created");
    public static final Column<LocalDateTime> PERSON_UPDATED = column("updated");
    public static final Column<String> PERSON_CITY = column("city");
    public static final Column<String> PERSON_COUNTRY = column("country");
    public static final Column<Integer> PERSON_AGE = column("age");
    public static final Table TBL_PERSON = table( "person", PERSON_ID);

    public PersonTable(String tableName, Column<Integer> idColumn) {
        super(TBL_PERSON.getTableName(), PERSON_ID);
    }

    public static final RowMapper<Person> PERSON_ROW_MAPPER = (rs) -> {
        final Person personRow = new Person();
        personRow.setId(rs.getInt(PERSON_ID.getColumnName()));
        personRow.setFirstName(rs.getString(PERSON_FIRST_NAME.getColumnName()));
        personRow.setLastName(rs.getString(PERSON_LAST_NAME.getColumnName()));
        personRow.setEmail(rs.getString(PERSON_EMAIL.getColumnName()));
        personRow.setAdult(rs.getBoolean(PERSON_IS_ADULT.getColumnName()));
        personRow.setCreated(rs.getTimestamp(PERSON_CREATED.getColumnName()).toLocalDateTime());
        if (rs.getTimestamp(PERSON_UPDATED.getColumnName()) != null) {
            personRow.setUpdated(rs.getTimestamp(PERSON_UPDATED.getColumnName()).toLocalDateTime());
        }
        personRow.setCity(rs.getString(PERSON_CITY.getColumnName()));
        personRow.setCountry(rs.getString(PERSON_COUNTRY.getColumnName()));
        personRow.setAge(rs.getInt(PERSON_AGE.getColumnName()));
        return personRow;
    };
}
