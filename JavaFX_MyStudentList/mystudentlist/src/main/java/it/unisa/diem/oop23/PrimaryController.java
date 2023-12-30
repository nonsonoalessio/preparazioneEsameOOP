package it.unisa.diem.oop23;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML private TableView<Student> t;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, Long> idColumn;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private TextField firstNameTf;
    @FXML private TextField lastNameTf;
    @FXML private TextField idTf;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label idLabel; 

    @FXML
    private void add(){
        Student s = new Student();
        s.setId(Long.parseLong(idTf.getText()));
        s.setFirstName(firstNameTf.getText());
        s.setLastName(lastNameTf.getText());
        t.getItems().add(s);
        idTf.clear();
        firstNameTf.clear();
        lastNameTf.clear();
    }

    @FXML
    private void delete(){
        ObservableList<Student> selected, all;
        all = t.getItems();
        selected = t.getSelectionModel().getSelectedItems();
        selected.forEach(all::remove);
    }

    public ObservableList<Student> getStudents(){
        return FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t.setItems(getStudents());
    }
}
