/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umar.simply.jdbc.ui.javafx;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.fluent.dao.person.PersonQueryService;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author contagious
 */
public class PersonViewController implements Initializable {

    @FXML
    private TableColumn<Person, Long> idColumn;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;
    @FXML
    private TableColumn<Person, String> cityColumn;
    @FXML
    private TableColumn<Person, String> countryColumn;
    @FXML
    private TableColumn<Person, Boolean> adultColumn;
    @FXML
    private TableColumn<Person, LocalDateTime> createdColumn;
    @FXML
    private TableColumn<Person, Integer> ageColumn;
    @FXML
    private TableView<Person> personTableView;
    

    public static ObservableList<Person> getPeople(){
        PersonQueryService pqs = new PersonQueryService(JdbcUtilService.getConnection());
        return FXCollections.<Person>observableArrayList(pqs.people());
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PropertyValueFactory<Person,Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);
        PropertyValueFactory<Person,String> fnameCellValueFactory = new PropertyValueFactory<>("firstName");
        firstNameColumn.setCellValueFactory(fnameCellValueFactory);
        PropertyValueFactory<Person,String> lnameCellValueFactory = new PropertyValueFactory<>("lastName");
        lastNameColumn.setCellValueFactory(lnameCellValueFactory);
        PropertyValueFactory<Person,String> emailCellValueFactory = new PropertyValueFactory<>("email");
        emailColumn.setCellValueFactory(emailCellValueFactory);
        PropertyValueFactory<Person,String> cityCellValueFactory = new PropertyValueFactory<>("city");
        cityColumn.setCellValueFactory(cityCellValueFactory);
        PropertyValueFactory<Person,String> countryCellValueFactory = new PropertyValueFactory<>("country");
        countryColumn.setCellValueFactory(countryCellValueFactory);
        PropertyValueFactory<Person,Boolean> adultCellValueFactory = new PropertyValueFactory<>("adult");
        adultColumn.setCellValueFactory(adultCellValueFactory);
        PropertyValueFactory<Person,LocalDateTime> createdCellValueFactory = new PropertyValueFactory<>("created");
        createdColumn.setCellValueFactory(createdCellValueFactory);
        PropertyValueFactory<Person,Integer> ageCellValueFactory = new PropertyValueFactory<>("age");
        ageColumn.setCellValueFactory(ageCellValueFactory);
        personTableView.getItems().addAll(getPeople());
    }    
    
    
}
