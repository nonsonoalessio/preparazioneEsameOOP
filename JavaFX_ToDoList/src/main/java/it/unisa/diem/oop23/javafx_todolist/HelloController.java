package it.unisa.diem.oop23.javafx_todolist;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelloController implements Initializable {
    @FXML private TextField eventTf;
    @FXML private DatePicker date;
    @FXML private Button addButton;
    @FXML private TableView<Reminder> table;
    @FXML private TableColumn<Reminder, LocalDate> dateColumn;
    @FXML private TableColumn<Reminder, String> eventColumn;
    @FXML private MenuItem remove;
    @FXML private MenuItem importList;
    @FXML private MenuItem exportList;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Reminder> list = FXCollections.observableArrayList();
        table.setItems(list);
        TimedSaving savingService = new TimedSaving(table.getItems());

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        eventColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        date.setValue(LocalDate.now());

        BooleanBinding addButtonControl = Bindings.createBooleanBinding(() -> eventTf.getText().isEmpty(),
                eventTf.textProperty());
        addButton.disableProperty().bind(addButtonControl);

        BooleanBinding emptyListControl = Bindings.createBooleanBinding(() -> table.getItems().isEmpty(),
                table.getItems());
        remove.disableProperty().bind(emptyListControl);
        exportList.disableProperty().bind(emptyListControl);
    }

    @FXML
    private void addButtonOnClick(){
        Reminder r = new Reminder(date.getValue(), eventTf.getText());
        synchronized (table.getItems()) {
            table.getItems().add(r);
            table.getItems().sort(null);
            table.getItems().notifyAll();
        }
            eventTf.clear();
    }

    @FXML
    private void editEvent(TableColumn.CellEditEvent<Reminder, String> e){
        Reminder r = table.getSelectionModel().getSelectedItem();
        r.setEvent(e.getNewValue());
    }

    @SuppressWarnings("CallToPrintStackTrace")
    @FXML
    private void exportListOnClick(ActionEvent e){
        File f;
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"));
        f = saver.showSaveDialog(stage);

        try(FileWriter w = new FileWriter(f.getAbsolutePath())){
            w.write("Date;eventDescription\n");
            for(Reminder r : table.getSelectionModel().getSelectedItems()){
                w.write(r.getDate().toString());
                w.write(";");
                w.write(r.getEvent().replaceAll(";", "|"));
                w.write("\n");
            }
        } catch (IOException exc){
            exc.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    @FXML
    private void importListOnClick(ActionEvent e){
        File f;
        FileChooser importer = new FileChooser();
        importer.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"));
        f = importer.showOpenDialog(stage);
        try(Scanner s = new Scanner(new FileReader(f.getAbsolutePath()))){
            s.nextLine();
            s.useDelimiter("[\n;]");
            table.getItems().removeAll();
            while(s.hasNext()){
                Reminder r = new Reminder();
                r.setDate(LocalDate.parse(s.next()));
                r.setEvent(s.next().replaceAll("[|]", ";"));
                table.getItems().add(r);
            }
        } catch (FileNotFoundException exc){
            exc.printStackTrace();
        }
    }

    @FXML
    private void removeItemOnClick(){
        table.getSelectionModel().getSelectedItems().forEach(table.getItems()::remove);
    }

}