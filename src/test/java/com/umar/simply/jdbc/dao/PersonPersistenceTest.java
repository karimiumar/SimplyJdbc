package com.umar.simply.jdbc.dao;


import java.util.List;

import static com.umar.simply.jdbc.dao.Person.TblPerson.fname;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;
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
        dps.delete().where().anyColumnValues(asList(set(fname,"Lucy"))).execute();
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
    }

    @Test
    @Order(2)
    public void updatePerson(){
        PersonUpdateService updateService = new PersonUpdateService();
        PersonQueryService<Person> queryLucy = new PersonQueryService<>();
        List<Person> people = queryLucy.people().stream().filter(p->p.getEmail().equals("lucy@gmail.com")).collect(Collectors.toList());
        Person p1 = people.get(0);
        Person p1NewVal = new Person("Lucy", "Liu", "lucy@gmail.com", true, "Beijing", "China", 34);
        p1 = updateService.update(p1).with(p1NewVal).execute();
        Assertions.assertEquals("Beijing", p1.getCity());
    }

    @Test
    @Order(3)
    public void listAll(){
        PersonQueryService pqs = new PersonQueryService();
        List<Person> people = pqs.people();
        Assertions.assertTrue(people.size() > 0);
        Assertions.assertEquals(2, people.size());
    }

}
