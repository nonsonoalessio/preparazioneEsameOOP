package alessiodonatogiura;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML private Label question;
    @FXML private Label progress;
    @FXML private Label exportInvite;
    @FXML private Button startButton;
    @FXML private TextField firstNameTf;
    @FXML private TextField lastNameTf;
    @FXML private TextField questionsTf;
    @FXML private TextField answerTf;
    @FXML private TableView<NumericQuestionAttempt> recap;
    @FXML private TableColumn<NumericQuestionAttempt, String> operation;
    @FXML private TableColumn<NumericQuestionAttempt, String> esit;
    @FXML private StackPane view;
    @FXML private VBox account;
    @FXML private VBox qa;
    @FXML private VBox recapView;

    private ArrayList<NumericQuestion> listOfQuestions;
    private ArrayList<NumericQuestionAttempt> listOfAnswers;
    private int currentProgress = 1;
    private Stage s;
    private ObservableList<NumericQuestionAttempt> recapList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operation.setCellValueFactory(new PropertyValueFactory<>("displayableAttempt"));
        esit.setCellValueFactory(new PropertyValueFactory<>("correct"));

        BooleanBinding startButtonControl = Bindings.createBooleanBinding(() -> firstNameTf.getText().isEmpty() || lastNameTf.getText().isEmpty() || questionsTf.getText().isEmpty(),
                firstNameTf.textProperty(), lastNameTf.textProperty(), questionsTf.textProperty());
        startButton.disableProperty().bind(startButtonControl);
    }

    @FXML
    private void exportButtonOnClick(){
        File f;
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(new FileChooser.ExtensionFilter("File di testo", "*.txt"));
        f = saver.showSaveDialog(s);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(f.getAbsolutePath()))) {
            writer.write("TENTATIVO;RISULTATO CORRETTO;ESITO\n");
            for(NumericQuestionAttempt i : listOfAnswers){
                writer.write(i.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void startButtonOnClick() {
        if(Integer.parseInt(questionsTf.getText()) <= 0){
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setTitle("Errore");
            alertBox.setHeaderText("Errore");
            alertBox.setContentText("Scegliere un valore intero positivo");
            alertBox.showAndWait();
        } else {
            listOfQuestions = new ArrayList<>();
            listOfAnswers = new ArrayList<>();
            for(int i = 0; i < Integer.parseInt(questionsTf.getText()); i++) {
                listOfQuestions.add(new NumericQuestion());
            }
            question.setText(listOfQuestions.get(0).toString());
            progress.setText("1/" + questionsTf.getText());
            exportInvite.setText("Gentile " + firstNameTf.getText() + " " + lastNameTf.getText() + ", grazie per aver completato il quiz. Esporta i tuoi risultati su file.");
            account.setVisible(false);
            qa.setVisible(true);

        }
    }

    @FXML
    private void doneButtonOnClick(){
            listOfAnswers.add(new NumericQuestionAttempt(listOfQuestions.get(currentProgress - 1), Integer.parseInt(answerTf.getText())));
            currentProgress += 1;
            answerTf.clear();
            if(currentProgress == Integer.parseInt(questionsTf.getText()) + 1){
                recapList = FXCollections.observableArrayList(listOfAnswers);
                recap.setItems(recapList);
                qa.setVisible(false);
                recapView.setVisible(true);
            } else {
            question.setText(listOfQuestions.get(currentProgress - 1).toString());
            progress.setText(currentProgress + "/" + questionsTf.getText());
            }
    }
}
