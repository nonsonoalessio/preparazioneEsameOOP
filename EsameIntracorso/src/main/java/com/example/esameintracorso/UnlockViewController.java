package com.example.esameintracorso;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UnlockViewController implements Initializable {
    private Stage stage;
    @FXML private Label generatedOTP;
    @FXML private PasswordField insOTP;
    @FXML private Button unlockButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void unlockMethod() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("phone-book-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("My Secret Phone Book");
        stage.setScene(scene);
        stage.show();
    }
}