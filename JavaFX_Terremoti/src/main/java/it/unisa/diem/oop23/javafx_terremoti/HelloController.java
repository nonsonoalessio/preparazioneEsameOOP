package it.unisa.diem.oop23.javafx_terremoti;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Consumer;

public class HelloController implements Initializable {

    private Stage stage;

    @FXML private MenuItem saveToBin;
    @FXML private MenuItem operFromBin;
    @FXML private MenuItem exportToText;
    @FXML private MenuItem importFromText;
    @FXML private MenuItem quit;

    @FXML private TableView<EQEvent> report;

    @FXML private TableColumn<EQEvent, Long> eventIDColumn;
    @FXML private TableColumn<EQEvent, LocalDateTime> timeColumn;
    @FXML private TableColumn<EQEvent, Double> latitudeColumn;
    @FXML private TableColumn<EQEvent, Double> longitudeColumn;
    @FXML private TableColumn<EQEvent, Double> depthKmColumn;
    @FXML private TableColumn<EQEvent, String> authorColumn;
    @FXML private TableColumn<EQEvent, String> catalogColumn;
    @FXML private TableColumn<EQEvent, String> contributorColumn;
    @FXML private TableColumn<EQEvent, Long> contributorIDColumn;
    @FXML private TableColumn<EQEvent, String> magTypeColumn;
    @FXML private TableColumn<EQEvent, Double> magnitudeColumn;
    @FXML private TableColumn<EQEvent, String> magAuthorColumn;
    @FXML private TableColumn<EQEvent, String> eventLocationNameColumn;
    @FXML private TableColumn<EQEvent, String> eventTypeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        depthKmColumn.setCellValueFactory(new PropertyValueFactory<>("depthKm"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        catalogColumn.setCellValueFactory(new PropertyValueFactory<>("catalog"));
        contributorColumn.setCellValueFactory(new PropertyValueFactory<>("contributor"));
        contributorIDColumn.setCellValueFactory(new PropertyValueFactory<>("contributorID"));
        magTypeColumn.setCellValueFactory(new PropertyValueFactory<>("magType"));
        magnitudeColumn.setCellValueFactory(new PropertyValueFactory<>("magnitude"));
        magAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("magAuthor"));
        eventLocationNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocationName"));
        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));

        report.setItems(getEQEvents());

        BooleanBinding saveMenuControl = Bindings.createBooleanBinding( () -> report.getItems().isEmpty(), report.getItems());

        saveToBin.disableProperty().bind(saveMenuControl);
    }

    private ObservableList<EQEvent> getEQEvents(){
        return FXCollections.observableArrayList();
    }

    @FXML
    private void saveBinary(){
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Binario (.bin)", "*.bin"));
        saver.setInitialDirectory(new File(System.getProperty("user.home")));

        File f = saver.showSaveDialog(stage);

        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f.getAbsolutePath())))){
            ArrayList<EQEvent> writableList = new ArrayList<>(report.getItems());
            oos.writeObject(writableList);
            writableList = null;
            Alert doneSave = new Alert(Alert.AlertType.INFORMATION);
            doneSave.setTitle("Salvataggio riusito");
            doneSave.setHeaderText("Il report è stato salvato");
            doneSave.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
            Alert impossibileSave = new Alert(Alert.AlertType.ERROR);
            impossibileSave.setTitle("Errore nel salvataggio");
            impossibileSave.setHeaderText("Salvaggio non andato a buon fine");
            impossibileSave.setContentText("Il file non è stato salvato.");
            impossibileSave.showAndWait();
        }
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void openBinary(){
        FileChooser opener = new FileChooser();
        opener.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Binario (.bin)", "*.bin"));
        opener.setInitialDirectory(new File(System.getProperty("user.home")));

        File f = opener.showOpenDialog(stage);

        try(ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f.getAbsolutePath())))){
            ArrayList<EQEvent> readList;
            readList = (ArrayList<EQEvent>) oos.readObject();
            report.getItems().addAll(readList);
            readList = null;
            Alert doneOpen = new Alert(Alert.AlertType.INFORMATION);
            doneOpen.setTitle("Apertura riuscita");
            doneOpen.setHeaderText("Il report è stato aperto");
            doneOpen.showAndWait();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
            Alert impossibileOpen = new Alert(Alert.AlertType.ERROR);
            impossibileOpen.setTitle("Errore nell'apertura");
            impossibileOpen.setHeaderText("L'apertura non è andata a buon fine");
            impossibileOpen.setContentText("Impossibile aprire il file.");
            impossibileOpen.showAndWait();
        }
    }

    @FXML
    private void exportText(){
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(new FileChooser.ExtensionFilter("File di testo, Comma-Separated Values", "*.txt", "*.csv"));
        saver.setInitialDirectory(new File(System.getProperty("user.home")));

        File f = saver.showSaveDialog(stage);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(f.getAbsolutePath()))){
            writer.write("#EventID|Time|Latitude|Longitude|Depth/Km|Author|Catalog|Contributor|ContributorID|MagType|Magnitude|MagAuthor|EventLocationName|EventType\n");
            for(EQEvent e : report.getItems())
                writer.write(e.toString());
        } catch (IOException e){
            e.printStackTrace();
            Alert impossibileSave = new Alert(Alert.AlertType.ERROR);
            impossibileSave.setTitle("Errore nel salvataggio");
            impossibileSave.setHeaderText("Salvaggio non andato a buon fine");
            impossibileSave.setContentText("Il file non è stato salvato.");
            impossibileSave.showAndWait();
        }
    }

    @FXML
    private void importText(){
        FileChooser opener = new FileChooser();
        opener.getExtensionFilters().add(new FileChooser.ExtensionFilter("File di testo, Comma-Separated Values", "*.txt", "*.csv"));
        opener.setInitialDirectory(new File(System.getProperty("user.home")));

        File f = opener.showOpenDialog(stage);

        try(Scanner scan = new Scanner(new FileReader(f.getAbsolutePath()))){
            ArrayList<EQEvent> readFromFile = new ArrayList<>();
            scan.nextLine();
            scan.useDelimiter("[|\n]");
            while(scan.hasNext()){
                EQEvent e = new EQEvent();
                e.setEventID(scan.nextLong());
                System.out.println("read: " + e.getEventID());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                e.setTime(LocalDateTime.parse(scan.next(), formatter));
                System.out.println("read: " + e.getTime());
                e.setLatitude(Double.parseDouble(scan.next()));
                e.setLongitude(Double.parseDouble(scan.next()));
                e.setDepthKm(Double.parseDouble(scan.next()));
                e.setAuthor(scan.next());
                e.setCatalog(scan.next());
                e.setContributor(scan.next());
                String cID = scan.next();
                int intCID = 0;
                if(!cID.isEmpty())
                    intCID = Integer.parseInt(cID);
                e.setContributorID(intCID);
                e.setMagType(scan.next());
                e.setMagnitude(Double.parseDouble(scan.next()));
                e.setMagAuthor(scan.next());
                e.setEventLocationName(scan.next());
                e.setEventType(scan.next());
                readFromFile.add(e);
            }
            report.getItems().addAll(readFromFile);
        } catch (IOException e){
            e.printStackTrace();
            Alert impossibileOpen = new Alert(Alert.AlertType.ERROR);
            impossibileOpen.setTitle("Errore nell'apertura");
            impossibileOpen.setHeaderText("L'apertura non è andata a buon fine");
            impossibileOpen.setContentText("Impossibile aprire il file.");
            impossibileOpen.showAndWait();
        }
    }

    @FXML
    private void quit(){
        Platform.exit();
    }
}