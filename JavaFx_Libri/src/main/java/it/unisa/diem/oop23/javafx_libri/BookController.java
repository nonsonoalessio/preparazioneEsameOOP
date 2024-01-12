package it.unisa.diem.oop23.javafx_libri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    @FXML private TextField limitTf;
    @FXML private TextField minYearTf;
    @FXML private TextField maxYearTf;
    @FXML private TableView<Libro> tabella;
    @FXML private TableColumn<Libro, Long> isbnColumn;
    @FXML private TableColumn<Libro, Long> codVolColumn;
    @FXML private TableColumn<Libro, String> titoloColumn;
    @FXML private TableColumn<Libro, Integer> annoColumn;
    @FXML private TableColumn<Libro, Double> prezzoColumn;
    @FXML private TableColumn<Libro, Double> pesoColumn;
    @FXML private TableColumn<Libro, Integer> pagineColumn;
    @FXML private ProgressBar loadingProgress;
    @FXML private ComboBox<String> saintVincent;
    private URL u;
    ObservableList<Libro> libri = FXCollections.observableArrayList();


    @SuppressWarnings({"deprecation", "CallToPrintStackTrace"})
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL u = null;
        try{
            u = new URL("http://193.205.163.165/oopdata/Cat_Zani_ext.csv");
            this.u = u;
        } catch (Exception e){
            e.printStackTrace();
        }

        CaricaCatologoService initService = new CaricaCatologoService(u, -1, 1965, 2022, saintVincent, "");
        initService.start();

        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        codVolColumn.setCellValueFactory(new PropertyValueFactory<>("codVol"));
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        annoColumn.setCellValueFactory(new PropertyValueFactory<>("annoPrintable"));
        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        pagineColumn.setCellValueFactory(new PropertyValueFactory<>("pagine"));

        tabella.setItems(initService.getList());
    }

    @FXML
    private void updateButtonOnClick(){
        int limit;
        int minYear;
        int maxYear;
        String category;

        if (limitTf.getText().isEmpty())
            limit = -1;
        else
            limit = Integer.parseInt(limitTf.getText());

        if (minYearTf.getText().isEmpty())
            minYear = 1900;
        else
            minYear = Integer.parseInt(minYearTf.getText());

        if (maxYearTf.getText().isEmpty())
            maxYear = 2023;
        else
            maxYear = Integer.parseInt(maxYearTf.getText());

        if (saintVincent.getSelectionModel().getSelectedItem() == null)
            category = "";
        else
            category = saintVincent.getSelectionModel().getSelectedItem();


        CaricaCatologoService loader = new CaricaCatologoService(u, limit, minYear, maxYear, saintVincent, category);
        loadingProgress.progressProperty().bind(loader.progressProperty());

        loadingProgress.setVisible(true);
        loader.start();
        this.tabella.setItems(loader.getList());
        loadingProgress.setVisible(false);
    }
}