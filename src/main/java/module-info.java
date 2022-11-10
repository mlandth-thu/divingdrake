module de.mlandth.divingdrake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens de.mlandth.divingdrake to javafx.fxml;
    exports de.mlandth.divingdrake;
}