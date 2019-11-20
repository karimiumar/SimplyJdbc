package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Timestamp;
import java.util.Date;

import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import static com.umar.simply.jdbc.meta.Table.table;

public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean adult;
    private Date created;
    private String city;
    private String country;
    private Integer age;

    /*
        This represents database table.
        The interface declaration is just for classification. Its redundant otherwise
        Type safe column names declared here
     */
    public interface TblPerson {
        Column<Long> personId = column("id");
        Column<String> fname = column("firstname");
        Column<String> lname = column("lastname");
        Column<String> email = column("email");
        Column<Boolean> adult = column("adult");
        Column<Date> created = column("created");
        Column<String> city = column("city");
        Column<String> country = column("country");
        Column<Integer> age = column("age");
        Table person = table("person", personId);

        //Some column and table aliases comes handy for self joins

        Column<Long> p1_id = as("id", "p1");
        Column<String> p1_fname = as("firstname", "p1");
        Column<String> p1_lname = as("lastname", "p1");
        Column<String> p1_email = as("email", "p1");
        Column<Boolean> p1_adult = as("adult", "p1");
        Column<Date> p1_created = as("created", "p1");
        Column<String> p1_city = as("city", "p1");
        Column<String> p1_country = as("country", "p1");
        Column<Integer> p1_age = as("age", "p1");
        Table p1 = Table.as("person", "p1", personId);

        Column<Long> p2_id = as("id", "p2");
        Column<String> p2_fname = as("firstname", "p2");
        Column<String> p2_lname = as("lastname", "p2");
        Column<String> p2_email = as("email", "p2");
        Column<Boolean> p2_adult = as("adult", "p2");
        Column<Date> p2_created = as("created", "p2");
        Column<String> p2_city = as("city", "p2");
        Column<String> p2_country = as("country", "p2");
        Column<Integer> p2_age = as("age", "p2");
        Table p2 = Table.as("person", "p2", personId);

        RowMapper<Person> PERSON_ROW_MAPPER = (rs) -> {
            final Person person = new Person();
            person.id = rs.getLong(personId.getColumnName());
            person.firstName = rs.getString(fname.getColumnName());
            person.lastName = rs.getString(lname.getColumnName());
            person.email = rs.getString(email.getColumnName());
            person.adult = rs.getBoolean(adult.getColumnName());
            person.created = rs.getTimestamp(created.getColumnName());
            person.city = rs.getString(city.getColumnName());
            person.country = rs.getString(country.getColumnName());
            person.age = rs.getInt(age.getColumnName());
            return person;
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

        if (!id.equals(person.id)) return false;
        if (!firstName.equals(person.firstName)) return false;
        if (!lastName.equals(person.lastName)) return false;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
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
}
