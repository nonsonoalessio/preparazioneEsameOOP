package it.unisa.diem.oop23.javafx_libri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    @FXML private TableView<Libro> tabella;
    @FXML private TableColumn<Libro, Long> isbnColumn;
    @FXML private TableColumn<Libro, Long> codVolColumn;
    @FXML private TableColumn<Libro, String> titoloColumn;
    @FXML private TableColumn<Libro, Integer> annoColumn;
    @FXML private TableColumn<Libro, Double> prezzoColumn;
    @FXML private TableColumn<Libro, Double> pesoColumn;
    @FXML private TableColumn<Libro, Integer> pagineColumn;
    @FXML private ProgressBar loadingProgress;
    @FXML private ComboBox<String> comboBox;


    @SuppressWarnings({"deprecation", "CallToPrintStackTrace"})
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL u = null;
        try{
            u = new URL("http://193.205.163.165/oopdata/Cat_Zani_ext.csv");
        } catch (Exception e){
            e.printStackTrace();
        }

        CaricaCatologoService initService = new CaricaCatologoService(u, -1, 1965, 2022, loadingProgress, comboBox);
        initService.start();

        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        codVolColumn.setCellValueFactory(new PropertyValueFactory<>("codVol"));
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        annoColumn.setCellValueFactory(new PropertyValueFactory<>("anno"));
        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        pagineColumn.setCellValueFactory(new PropertyValueFactory<>("pagine"));

        // TODO: Rivedere implementazione del Service (ritorna null ArrayList).
        ObservableList<Libro> libri = FXCollections.observableArrayList(initService.getValue());
        tabella.setItems(libri);
    }

    @FXML
    private void updateButtonOnClick(){
        // TODO: Implementazione updateButtonOnClick()
    }
}