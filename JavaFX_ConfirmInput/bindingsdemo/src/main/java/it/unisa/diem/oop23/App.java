package it.unisa.diem.oop23;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Label input = new Label("Input: ");
        Label confirmInput = new Label("Confirm input: ");
        TextField inputTf = new TextField();
        inputTf.setPromptText("Insert input here");
        TextField confirmTf = new TextField();
        confirmTf.setPromptText("Repeat input here");

        HBox inputBox = new HBox(10, input, inputTf);
        inputBox.setAlignment(Pos.CENTER);
        HBox confirmBox = new HBox(confirmInput, confirmTf);
        confirmBox.setAlignment(Pos.CENTER);

        Button b = new Button("Validate!");

        BooleanBinding validation = Bindings.createBooleanBinding(() -> inputTf.getText().equals(confirmTf.getText()) && !inputTf.getText().equals(""), inputTf.textProperty(), confirmTf.textProperty());

        b.disableProperty().bind(validation.not());
        b.setOnAction(e -> Platform.exit());

        VBox root = new VBox(10, inputBox, confirmBox, b);
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }
}