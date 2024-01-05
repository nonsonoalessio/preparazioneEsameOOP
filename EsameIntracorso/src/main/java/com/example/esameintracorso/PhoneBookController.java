package com.example.esameintracorso;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * La classe PhoneBookController implementa il controller necessario per la gestione dell'applicazione.
 * <br>
 * Contiene sia il necessario per la schermata di blocco, sia l'applicazione in sé.
 * @author Giura Alessio Donato
*/
public class PhoneBookController implements Initializable {

    // FXNL variables
    @FXML private Label generatedOTP;
    @FXML private PasswordField insOTP;
    @FXML private Button unlockButton;
    @FXML private TextField firstNameTf;
    @FXML private TextField lastNameTf;
    @FXML private TextField phoneNumberTf;
    @FXML private Button addButton;
    @FXML private TableView<Contact> book;
    @FXML private TableColumn<Contact, String> firstNameColumn;
    @FXML private TableColumn<Contact, String> lastNameColumn;
    @FXML private TableColumn<Contact, String> phoneNumberColumn;
    @FXML private VBox unlockScreen;
    @FXML private HBox mainApp;

    // Service related variables
    private final SimpleStringProperty otpCode = new SimpleStringProperty();
    private final Service<Void> generateOTPService = new Service<>(){
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @SuppressWarnings("BusyWait")
                @Override
                protected Void call() throws Exception {
                    Random r = new Random();
                    while(!isCancelled()){
                        Platform.runLater(() -> otpCode.setValue(String.valueOf(r.nextInt(500))));
                        Thread.sleep(10000);
                    }
                    return null;
                }
            };
        }
    };

    /**
     * Il metodo initialize consente l'inizializzazione di tutti i componenti.<br>
     * In particolare, si occupa di mostrare la schermata di sblocco, avviare il {@link javafx.concurrent.Service service}
     * deputato alla generazione del codice OTP, oltre che a creare i {@link javafx.beans.binding.BooleanBinding bindings}
     * necessari per l'invalidazione dei bottoni di aggiunta e sblocco quando il codice non è inserito o esatto (per
     * {@link com.example.esameintracorso.PhoneBookController#unlockButton}) oppure se i campi del
     * contatto non sono stati tutti compilati e/o compilato in modo errato, cioè se {@link com.example.esameintracorso.PhoneBookController#phoneNumberTf il numero telefonico}
     * inserito non è in formato numerico (per {@link com.example.esameintracorso.PhoneBookController#addButton}).<br>
     * Gestisce, inoltre, le colonne della {@link com.example.esameintracorso.PhoneBookController#book tabella}, impostandone
     * i tipi di valore e consentendo la modifica delle celle (evitando l'aggiunta di contatti duplicati).
     *
     * @param url non usato.
     * @param resourceBundle non usato.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainApp.setVisible(false);
        unlockScreen.setVisible(true);
        generateOTPService.start();

        BooleanBinding unlockButtonControl = Bindings.createBooleanBinding(() -> !insOTP.getText().equals(otpCode.getValue()),
                otpCode, insOTP.textProperty());

        generatedOTP.textProperty().bind(otpCode);
        unlockButton.disableProperty().bind(unlockButtonControl);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        book.setItems(contacts);
        book.setEditable(true);

        BooleanBinding addButtonControl = Bindings.createBooleanBinding(() -> firstNameTf.getText().isEmpty() ||
                lastNameTf.getText().isEmpty() ||
                phoneNumberTf.getText().isEmpty() ||
                !phoneNumberIsValid(),
                firstNameTf.textProperty(), lastNameTf.textProperty(), phoneNumberTf.textProperty());
        addButton.disableProperty().bind(addButtonControl);
    }

    // =================== GESTIONE DELLA SCHERMATA DI BLOCCO ===================
    /**
     * Il metodo unlockMethod viene invocato alla pressione di {@link com.example.esameintracorso.PhoneBookController#unlockButton unlockButton}
     * e consente lo sblocco dell'applicazione, gestendo le due view dello {@link javafx.scene.layout.StackPane StackPane}.<br>
     * Interrompe, inoltre il {@link javafx.concurrent.Service service} per la generazione del codice OTP ({@link com.example.esameintracorso.PhoneBookController#generateOTPService}).
    * */
    @FXML
    private void unlockMethod() {
        unlockScreen.setVisible(false);
        generateOTPService.cancel();
        mainApp.setVisible(true);
    }

    // =================== METODI DELLA SCHERMATA PRINCIPALE ===================

    /**
     * Il metodo addButtonOnClick viene invocato alla pressione di {@link com.example.esameintracorso.PhoneBookController#addButton}
     * e consente di aggiungere un {@link com.example.esameintracorso.Contact contatto} alla rubrica.<br>
     * Gestisce la presenza di eventuali duplicati, non consentendone l'inserimento (il tal caso è mostrata una {@link javafx.scene.control.Alert AlertBox} di errore)
     * e ripulendo i campi dopo l'inserimento.
     * */
    @FXML
    private void addButtonOnClick(){
        Contact c = new Contact();
        c.setFirstName(firstNameTf.getText());
        c.setLastName(lastNameTf.getText());
        c.setPhoneNumber(phoneNumberTf.getText());
        if(book.getItems().contains(c))
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Contatto già presente.");
        else
            book.getItems().add(c);
        firstNameTf.clear();
        lastNameTf.clear();
        phoneNumberTf.clear();
    }

    /**
     * Il metodo saveMenuItemOnClick viene invocato alla pressione del {@link javafx.scene.control.MenuItem} "Salva"
     * del {@link javafx.scene.control.Menu} "File" della {@link javafx.scene.control.MenuBar}.
     * Consente la scrittura su un file binario della lista dei contatti attualmente salvati; qualora non dovesse
     * riscontrare esito positivo, mostra una {@link javafx.scene.control.Alert alertbox} di errore.
     */
    @FXML
    private void saveMenuItemOnClick(){
        try{
            ArrayList<Contact> l = new ArrayList<>(book.getItems());
            Contact.writeContacts(l);
        } catch(IOException e){
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Impossibile Salvare il file");
        }
    }

    /**
     * Il metodo quitMenuItemOnClick viene invocato alla pressione del {@link javafx.scene.control.MenuItem} "Esci"
     * del {@link javafx.scene.control.Menu} "Esci" della {@link javafx.scene.control.MenuBar}.
     * Consente di uscire e terminare l'applicazione.
     */
    @FXML
    private void quitMenuItemOnClick(){
        Platform.exit();
    }

    /**
     * Il metodo deleteContactRightClick viene invocato alla pressione del {@link javafx.scene.control.MenuItem} "Cancella Contatto"
     * nel {@link javafx.scene.control.ContextMenu} associato a {@link com.example.esameintracorso.PhoneBookController#book}.
     * Consente la rimozione del {@link com.example.esameintracorso.Contact contatto} selezionato dalla rubrica.
     */
    @FXML
    private void deleteContactRightClick(){
        Contact c = book.getSelectionModel().getSelectedItem();
        book.getItems().remove(c);
    }

    /**
     * Il metodo copyContactRightClick viene invocato alla pressione del {@link javafx.scene.control.MenuItem} "Copia Contatto"
     * nel {@link javafx.scene.control.ContextMenu} associato a {@link com.example.esameintracorso.PhoneBookController#book}.
     * Consente la copia del {@link com.example.esameintracorso.Contact contatto} selezionato nel formato <code>nome;cognome;numero;</code>.
     */
    @FXML
    private void copyContactRightClick(){
        Contact c = book.getSelectionModel().getSelectedItem();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(c.toString());
        clipboard.setContent(content);
    }

    /**
     * Il metodo editFirstNameCell è associato all'evento di modifica di una cella della colonna relativa al nome ({@link PhoneBookController#firstNameColumn}). <br>
     * Consente la modifica del campo associato al nome di un {@link Contact contatto}, accertandosi che non vi siano duplicati (in tal caso, si mostra una {@link Alert AlertBox} di errore).
    */
    @FXML
    private void editFistNameCell(TableColumn.CellEditEvent<Contact, String> e){
        Contact selected = book.getSelectionModel().getSelectedItem();
        if(book.getItems().contains(new Contact(e.getNewValue(), selected.getLastName(), selected.getPhoneNumber()))) {
            book.refresh();
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Contatto già presente.");
        }else
            selected.setFirstName(e.getNewValue());
    }

    /**
     * Il metodo editLastNameCell è associato all'evento di modifica di una cella della colonna relativa al cognome ({@link PhoneBookController#lastNameColumn}).<br>
     * Consente la modifica del campo associato al cognome di un {@link Contact contatto}, accertandosi che non vi siano duplicati (in tal caso, si mostra una {@link Alert AlertBox} di errore).
     */
    @FXML
    private void editLastNameCell(TableColumn.CellEditEvent<Contact, String> e){
        Contact selected = book.getSelectionModel().getSelectedItem();
        if(book.getItems().contains(new Contact(selected.getFirstName(), e.getNewValue(), selected.getPhoneNumber()))) {
            book.refresh();
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Contatto già presente.");
        }
        else
            selected.setLastName(e.getNewValue());
    }

    /**
     * Il metodo editLastNameCell è associato all'evento di modifica di una cella della colonna relativa al numero di telefono ({@link PhoneBookController#phoneNumberColumn#onEditCommit}).<br>
     * Consente la modifica del campo associato al numero di telefono di un {@link Contact contatto}, accertandosi che non vi siano duplicati (in tal caso, si mostra una {@link Alert AlertBox} di errore).
     */
    @FXML
    private void editPhoneNumberCell(TableColumn.CellEditEvent<Contact, String> e){
        Contact selected = book.getSelectionModel().getSelectedItem();
        if(book.getItems().contains(new Contact(selected.getFirstName(), selected.getLastName(), e.getNewValue().toString())))
            showDialogueBox(Alert.AlertType.ERROR, "Errore", "Errore", "Contatto già presente.");
        else {
            if (phoneNumberIsValid(e.getNewValue()))
                selected.setPhoneNumber(e.getNewValue());
            else {
                book.refresh();
                showDialogueBox(Alert.AlertType.ERROR, "Manca il numero!", "Non è stato inserito un numero di telefono valido", "");
            }
        }
    }

    // =================== METODI DI UTILITÀ ===================

    /**
     * La funzione di utilità phoneNumberIsValid consente di controllare se il testo di {@link com.example.esameintracorso.PhoneBookController#phoneNumberTf}
     * sia effettivamente in forma numerica.
     * @return <code>true</code> se il valore immesso è un numero (intero);<br><code>false</code> se il valore non è un numero intero.
     */
    private boolean phoneNumberIsValid(){
        try{
            Long.parseLong(phoneNumberTf.getText());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * La funzione di utilità phoneNumberIsValid consente di controllare se il testo della {@link String stringa} s
     * sia effettivamente in forma numerica.
     * @param s la stringa da verifica.
     * @return <code>true</code> se il valore immesso è un numero (intero);<br><code>false</code> se il valore non è un numero intero.
     */
    private boolean phoneNumberIsValid(String s){
        try{
            Long.parseLong(s);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * La funzione di utilità showDialogueBox consente di mostrare una {@link javafx.scene.control.Alert AlertBox} e di attendere la sua chiusura
     * mediante il metodo {@link Alert#showAndWait()}, consentendo di definirne il tipo e i testi del box.
     * @param type il tipo di {@link Alert} da creare;
     * @param title il titolo dell'{@link Alert};
     * @param header il testo della parte superiore dell'{@link Alert};
     * @param content il testo della parte inferiore dell'{@link Alert}.
     */
    @SuppressWarnings("SameParameterValue")
    private void showDialogueBox(Alert.AlertType type, String title, String header, String content){
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
