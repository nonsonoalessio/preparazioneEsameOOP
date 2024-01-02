module com.example.esameintracorso {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.esameintracorso to javafx.fxml;
    exports com.example.esameintracorso;
}