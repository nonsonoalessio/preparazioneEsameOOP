module it.unisa.diem.oop23 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens it.unisa.diem.oop23 to javafx.fxml;
    exports it.unisa.diem.oop23;
}
