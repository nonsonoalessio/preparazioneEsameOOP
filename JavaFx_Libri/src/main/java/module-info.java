module it.unisa.diem.oop23.javafx_libri {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unisa.diem.oop23.javafx_libri to javafx.fxml;
    exports it.unisa.diem.oop23.javafx_libri;
}