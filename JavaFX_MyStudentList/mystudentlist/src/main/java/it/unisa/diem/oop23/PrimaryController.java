package it.unisa.diem.oop23;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
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
    @FXML private MenuBar mBar;
    @FXML private Menu fileMenu;
    @FXML private MenuItem save;
    @FXML private MenuItem quit;
    @FXML private MenuItem open;

    @FXML private Stage stage;

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
        BooleanBinding deleteButtonControl = Bindings.createBooleanBinding(() -> t.getItems().isEmpty() &&
                t.getSelectionModel().getSelectedItems().isEmpty(),
                t.getItems()
        );
        deleteButton.disableProperty().bind(deleteButtonControl);
        BooleanBinding addButtonControl = Bindings.createBooleanBinding(() -> firstNameTf.getText().isEmpty() ||
                lastNameTf.getText().isEmpty() ||
                idTf.getText().isEmpty() ||
                !tfIsInteger(),
                firstNameTf.textProperty(), lastNameTf.textProperty(), idTf.textProperty()
        );
        addButton.disableProperty().bind(addButtonControl);
        // TODO: Mostrare perché add è disabilitato.
        // TODO: Evitare aggiunta studente matricola già presente.

        BooleanBinding saveMenuItemControl = Bindings.createBooleanBinding(() -> t.getItems().isEmpty(), t.getItems());
        save.disableProperty().bind(saveMenuItemControl);
    }

    private boolean tfIsInteger(){
        try{
            Integer.parseInt(idTf.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    @FXML
    private void saveToFile(){
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(new FileChooser.ExtensionFilter("File binario (*.bin)", "*.bin"));
        saver.setInitialDirectory(new File(System.getProperty("user.home")));
        File f = saver.showSaveDialog(stage);
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f.getAbsolutePath())))){
            LinkedList<Student> list = new LinkedList<>(t.getItems());
            oos.writeObject(list);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Salvataggio completato");
            a.setHeaderText("Salvataggio effettuato con successo.");
            a.showAndWait();
        } catch (IOException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Errore");
            a.setHeaderText("Impossibile salvare il file.");
            a.setContentText("Si è verificato un errore durante il salvataggio del file.");
            a.showAndWait();
        }
    }
    @FXML
    private void quit(){
        Platform.exit();
    }
    @FXML
    @SuppressWarnings("unchecked")
    private void openFromFile(){
        FileChooser picker = new FileChooser();
        picker.getExtensionFilters().add(new FileChooser.ExtensionFilter("File binario (.bin)", "*.bin"));
        picker.setInitialDirectory(new File(System.getProperty("user.home")));
        File f = picker.showOpenDialog(stage);
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f.getAbsolutePath())))){
            LinkedList<Student> s = (LinkedList<Student>) ois.readObject();
            t.getItems().addAll(s);
        } catch (NullPointerException e){}
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
            a.setTitle("Errore");
            a.setHeaderText("Impossibile aprire il file.");
            a.setContentText("Si è verificato un errore durante l'apertura del file.");
            a.showAndWait();
        }
    }
}
