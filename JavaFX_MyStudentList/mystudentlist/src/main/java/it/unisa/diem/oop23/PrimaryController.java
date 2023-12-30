package it.unisa.diem.oop23;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class PrimaryController {

    @FXML
    private void add(){}

    @FXML
    private void delete(){}

    public ObservableList<Student> getStudents(){
        return FXCollections.observableArrayList();
    }    
}
