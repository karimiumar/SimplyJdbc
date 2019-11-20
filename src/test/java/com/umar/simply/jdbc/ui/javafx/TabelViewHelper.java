package com.umar.simply.jdbc.ui.javafx;

import com.umar.simply.jdbc.dao.Person;
import com.umar.simply.jdbc.dao.PersonQueryService;
import com.umar.simply.jdbc.dao.PersonSaveService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class TabelViewHelper {

    static{

        PersonSaveService pss = new PersonSaveService();
        Person p1 = new Person("Lucy", "Liu", "lucy@gmail.com", true, "Shanghai", "China", 34);
        Person p2 = new Person("Tina", "Turner", "tina@gmail.com", true, "Dettorite", "US", 34);
        //pss.save(p1).execute();
        //pss.save(p2).execute();
    }

    public static ObservableList<Person> getPeople(){
        PersonQueryService pqs = new PersonQueryService();
        return FXCollections.<Person>observableArrayList(pqs.people());
    }

    public static TableColumn<Person, Long> idColumn(){
        TableColumn<Person, Long> idColumn = new TableColumn<>("Id");
        PropertyValueFactory<Person,Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);
        return idColumn;
    }

    public static TableColumn<Person, String> firstNameColumn(){
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        PropertyValueFactory<Person,String> fnameCellValueFactory = new PropertyValueFactory<>("firstName");
        firstNameColumn.setCellValueFactory(fnameCellValueFactory);
        return firstNameColumn;
    }

    public static TableColumn<Person, String> lastNameColumn(){
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        PropertyValueFactory<Person,String> lnameCellValueFactory = new PropertyValueFactory<>("lastName");
        lastNameColumn.setCellValueFactory(lnameCellValueFactory);
        return lastNameColumn;
    }

    public static TableColumn<Person, String> emailColumn(){
        TableColumn<Person, String> emailColumn = new TableColumn<>("Email");
        PropertyValueFactory<Person,String> emailCellValueFactory = new PropertyValueFactory<>("email");
        emailColumn.setCellValueFactory(emailCellValueFactory);
        return emailColumn;
    }

    public static TableColumn<Person, String> cityColumn(){
        TableColumn<Person, String> cityColumn = new TableColumn<>("City");
        PropertyValueFactory<Person,String> cityCellValueFactory = new PropertyValueFactory<>("city");
        cityColumn.setCellValueFactory(cityCellValueFactory);
        return cityColumn;
    }

    public static TableColumn<Person, String> countryColumn(){
        TableColumn<Person, String> countryColumn = new TableColumn<>("Country");
        PropertyValueFactory<Person,String> countryCellValueFactory = new PropertyValueFactory<>("country");
        countryColumn.setCellValueFactory(countryCellValueFactory);
        return countryColumn;
    }

    public static TableColumn<Person, Boolean> adultColumn(){
        TableColumn<Person, Boolean> adultColumn = new TableColumn<>("Adult");
        PropertyValueFactory<Person,Boolean> adultCellValueFactory = new PropertyValueFactory<>("adult");
        adultColumn.setCellValueFactory(adultCellValueFactory);
        return adultColumn;
    }

    public static TableColumn<Person, Timestamp> createdColumn(){
        TableColumn<Person, Timestamp> createdColumn = new TableColumn<>("Created");
        PropertyValueFactory<Person,Timestamp> createdCellValueFactory = new PropertyValueFactory<>("created");
        createdColumn.setCellValueFactory(createdCellValueFactory);
        return createdColumn;
    }

    public static TableColumn<Person, Integer> ageColumn(){
        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        PropertyValueFactory<Person,Integer> ageCellValueFactory = new PropertyValueFactory<>("age");
        ageColumn.setCellValueFactory(ageCellValueFactory);
        return ageColumn;
    }
}
