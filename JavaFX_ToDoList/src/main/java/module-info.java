module it.unisa.diem.oop23.javafx_todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unisa.diem.oop23.javafx_todolist to javafx.fxml;
    exports it.unisa.diem.oop23.javafx_todolist;
}