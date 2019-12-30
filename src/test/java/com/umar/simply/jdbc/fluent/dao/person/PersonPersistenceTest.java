package com.umar.simply.jdbc.fluent.dao.person;


import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class PersonPersistenceTest {

    @AfterAll 
    public static void clean() {
        DeletePersonService dps = new DeletePersonService();
        dps.delete().where().anyColumnValues(asList(set(PERSON_FIRST_NAME,"Lucy"))).execute();
        dps.delete().execute();
    }

    @Test
    @Order(1)
    public void savePerson(){
        PersonSaveService saveService = new PersonSaveService();
        Person p1 = new Person("Lucy", "Liu", "lucy@gmail.com", true, "Shanghai", "China", 34);
        Person p2 = new Person("Lucy", "Pinder", "lucypinder@gmail.com", true, "Dettorite", "US", 34);
        p1 = saveService.save(p1).execute();
        Assertions.assertTrue(p1.getId() > 0);
        p2 = saveService.save(p2).execute();
        Assertions.assertTrue(p2.getId() > 0);
        Person umar = new Person("Mohammad Umar", "Ali Karimi", "karimiumar@hotmail.com", true, "India", "Nainital", 37);
        saveService.save(umar).execute();
    }

    @Test
    @Order(2)
    public void updatePerson(){
        PersonUpdateService updateService = new PersonUpdateService();
        PersonQueryService queryLucy = new PersonQueryService(JdbcUtilService.getConnection());
        List<Person> people = queryLucy.people().stream().filter(p->p.getEmail().equals("lucy@gmail.com")).collect(Collectors.toList());
        Person p1 = people.get(0);
        Person p1NewVal = new Person("Lucy", "Liu", "lucy@gmail.com", true, "Beijing", "China", 34);
        p1 = updateService.update(p1).with(p1NewVal).execute();
        Assertions.assertEquals("Beijing", p1.getCity());
    }

    @Test
    @Order(3)
    public void listAll(){
        PersonQueryService pqs = new PersonQueryService(JdbcUtilService.getConnection());
        List<Person> people = pqs.people();
        Assertions.assertTrue(people.size() > 0);
        Assertions.assertEquals(3, people.size());
    }
    
    @Test
    public void findByNameLucy() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Lucy";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertEquals(2, result.size());
    }
    
    @Test
    public void findByNamePinder() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Pinder";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertEquals(1, result.size());
    }
    
    @Test
    public void findByNameLucyPinder() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Lucy Pinder";
        List<Person> result = query.findByName(name); //search result will return two Lucy ->{Lucy Liu, Lucy Pinder}
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertEquals(2, result.size());
    }
    
    @Test
    public void findByNameLucyLiu() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Lucy Liu";
        List<Person> result = query.findByName(name); //search result will return two Lucy ->{Lucy Liu, Lucy Pinder}
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertEquals(2, result.size());
    }
    
    @Test
    public void findByNameGeorgeMichael() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "George Michael";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    public void findByNameGeorgeLeeMichael() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "George Lee Michael";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    public void findByNameDonGeorgeLeeMichael() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Don George Lee Michael";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    public void findByNameBlank() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = " ";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    public void findByNameEmpty() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    public void findByNameNull() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = null;
        Exception assertThrows = Assertions.assertThrows(RuntimeException.class, ()->query.findByName(name));
        Assertions.assertTrue(assertThrows.getMessage().contains("parameter <name> is required"));
    }
    
    /**
     * Given query results in a single result because firstName column contains "Mohammad Umar"
     * and lastName contains "Ali Karimi". However the search query will split the name into
     * following indexes and search only [0]th and [1]st index with the 'like' keyword.
     * [0]=Mohammad
     * [1]=Umar
     * [2]=Ali
     * [3]=Karimi
     */
    @Test
    public void findUmarByFullName() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Mohammad Umar Ali Karimi";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(!result.isEmpty());
    }
    
    /**
     * Given query results in a single result because firstName column contains "Mohammad Umar"
     * and lastName contains "Ali Karimi". However the search query will split the name into
     * following indexes and search only [0]th and [1]st index with the 'like' keyword.
     * [0]=Mohammad
     * [1]=Umar
     * 
     */
    @Test
    public void findUmarByMohammdUmar() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        String name = "Mohammad Umar";
        List<Person> result = query.findByName(name);
        Assertions.assertTrue(!result.isEmpty());
    }
    
    @Test
    public void findByIdIsEmptyResult() {
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        Optional<Person> optional = query.findById(PersonTable.setId(0));
        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    public void findByIdNonEmpty() {
        PersonSaveService saveService = new PersonSaveService();
        Person p1 = new Person("Jasmine", "J", "jj@gmail.com", true, "New Delhi", "India", 29);
        p1 = saveService.save(p1).execute();
        int id = p1.getId();
        PersonQueryService query = new PersonQueryService(JdbcUtilService.getConnection());
        Optional<Person> optional = query.findById(PersonTable.setId(id));
        Assertions.assertTrue(!optional.isEmpty());
    }
}
