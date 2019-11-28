package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.Table;

import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import static com.umar.simply.jdbc.meta.Table.table;
import java.time.LocalDateTime;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean adult;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String city;
    private String country;
    private Integer age;

    /*
        This represents database table.
        The interface declaration is just for classification. Its redundant otherwise
        Type safe column names declared here
     */
    public interface TblPerson {
        final String EX_SCHEMA = "ex";
        Column<Integer> personId = column("id");
        Column<String> fname = column("firstname");
        Column<String> lname = column("lastname");
        Column<String> email = column("email");
        Column<Boolean> adult = column("adult");
        Column<LocalDateTime> created = column("created");
        Column<LocalDateTime> updated = column("updated");
        Column<String> city = column("city");
        Column<String> country = column("country");
        Column<Integer> age = column("age");
        Table person = table(EX_SCHEMA +".person", personId);

        RowMapper<Person> PERSON_ROW_MAPPER = (rs) -> {
            final Person personRow = new Person();
            personRow.id = rs.getInt(personId.getColumnName());
            personRow.firstName = rs.getString(fname.getColumnName());
            personRow.lastName = rs.getString(lname.getColumnName());
            personRow.email = rs.getString(email.getColumnName());
            personRow.adult = rs.getBoolean(adult.getColumnName());
            personRow.created = rs.getTimestamp(created.getColumnName()).toLocalDateTime();
            if(rs.getTimestamp(updated.getColumnName()) != null) {
                personRow.updated = rs.getTimestamp(updated.getColumnName()).toLocalDateTime();
            }
            personRow.city = rs.getString(city.getColumnName());
            personRow.country = rs.getString(country.getColumnName());
            personRow.age = rs.getInt(age.getColumnName());
            return personRow;
        };
    }

    public Person (){}

    public Person(String firstName, String lastName, String email, Boolean adult, String city, String country, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.adult = adult;
        this.city = city;
        this.country = country;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        if (id == person.id) return true;
        if (!firstName.equals(person.firstName)) return false;
        if (!lastName.equals(person.lastName)) return false;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        int result = id * 31^7;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return String.format("Person(id:%d,firstName:%s,lastName:%s,email:%s,adult:%s,city:%s, country:%s, created:%s, updated:%s)", id, firstName, lastName, email, adult, city, country,created, updated);
    }
}
