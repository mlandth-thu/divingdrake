module de.mlandth.divingdrake {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.mlandth.divingdrake to javafx.fxml;
    exports de.mlandth.divingdrake;
}