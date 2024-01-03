package com.example.esameintracorso;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PhoneBookController implements Initializable {
    @FXML private TextField firstNameTf;
    @FXML private TextField lastNameTf;
    @FXML private TextField phoneNumberTf;
    @FXML private Button addButton;
    @FXML private TableView<Contact> book;
    @FXML private TableColumn<Contact, String> firstNameColumn;
    @FXML private TableColumn<Contact, String> lastNameColumn;
    @FXML private TableColumn<Contact, Long> phoneNumberColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Contact, Long>("phoneNumber"));
        book.setItems(getContacts());

        BooleanBinding addButtonControl = Bindings.createBooleanBinding(() -> firstNameTf.getText().isEmpty() && lastNameTf.getText().isEmpty() && phoneNumberIsValid(), firstNameTf.textProperty(), lastNameTf.textProperty(), phoneNumberTf.textProperty());
        addButton.disableProperty().bind(addButtonControl);
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
    private void addButtonOnClick(){}

    @FXML
    private void contactOnRightClick(){}

    @FXML
    private void saveMenuItemOnClick(){}

    @FXML
    private void quitMenuItemOnClick(){}
}
