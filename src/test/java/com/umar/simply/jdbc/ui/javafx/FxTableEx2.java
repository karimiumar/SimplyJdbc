package com.umar.simply.jdbc.ui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxTableEx2 extends Application {
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PersonView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //ScenicView.show(scene);
        scene.getStylesheets().add(this.getClass().getResource("personview.css").toExternalForm());
        primaryStage.setTitle("TableView Demo");
        primaryStage.show();
    }
}
