package com.example.esameintracorso;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PhoneBookController implements Initializable {
    @FXML private TextField firstNameTf;
    @FXML private TextField lastNameTf;
    @FXML private TextField phoneNumberTf;
    @FXML private Button addButton;
    @FXML private TableView<Contact> book;
    @FXML private TableColumn<Contact, String> firstNameColumn;
    @FXML private TableColumn<Contact, String> lastNameColumn;
    @FXML private TableColumn<Contact, String> phoneNumberColumn;
    @FXML private ContextMenu contextMenu;

    private Stage s;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        book.setItems(getContacts());

        BooleanBinding addButtonControl = Bindings.createBooleanBinding(() -> firstNameTf.getText().isEmpty() && lastNameTf.getText().isEmpty() && phoneNumberIsValid(), firstNameTf.textProperty(), lastNameTf.textProperty(), phoneNumberTf.textProperty());
        addButton.disableProperty().bind(addButtonControl.not());
    }

    private ObservableList<Contact> getContacts(){
        return FXCollections.observableArrayList();
    }

    private boolean phoneNumberIsValid(){
        try{
            Long.parseLong(phoneNumberTf.getText());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @FXML
    private void addButtonOnClick(){
        Contact c = new Contact();
        c.setFirstName(firstNameTf.getText());
        c.setLastName(lastNameTf.getText());
        c.setPhoneNumber(Long.parseLong(phoneNumberTf.getText()));
        if(book.getItems().contains(c))
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Contatto già presente.");
        else
            book.getItems().add(c);
        firstNameTf.clear();
        lastNameTf.clear();
        phoneNumberTf.clear();
    }

    @FXML
    private void saveMenuItemOnClick(){
        try{
            ArrayList<Contact> l = new ArrayList<>(book.getItems());
            Contact.writeContacts(l);
        } catch(IOException e){
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Impossibile Salvare il file");
        }
    }

    @FXML
    private void quitMenuItemOnClick(){
        Platform.exit();
    }

    @FXML
    private void deleteContactRightClick(){}

    @FXML
    private void copyContactRightClick(){}

    private void showDialogueBox(Alert.AlertType type, String title, String header, String content){
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
