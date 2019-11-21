package com.umar.simply.jdbc.ui.javafx;

import com.umar.simply.jdbc.fluent.dao.person.Person;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.umar.simply.jdbc.ui.javafx.TabelViewHelper.*;

public class FxTableEx1 extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane tabs = new TabPane();
        Tab peopleTab = new Tab();
        peopleTab.setText("People");

        TableView<Person> table = new TableView<>();
        table.getItems().addAll(TabelViewHelper.getPeople());
        table.getColumns().addAll(idColumn(), firstNameColumn(), lastNameColumn(), emailColumn(), adultColumn(), cityColumn(), countryColumn(), ageColumn(), createdColumn());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No visible column and/or data exist"));
        //table.setId("table");
        peopleTab.setContent(table);
        VBox root = new VBox();
        tabs.getTabs().add(peopleTab);
        root.getChildren().add(tabs);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("com/umar/simply/jdbc/ui/javafx/css/blue-table.css");
        table.getStyleClass().add("table-view");
        primaryStage.setTitle("TableView Demo");
        primaryStage.show();
    }
}
