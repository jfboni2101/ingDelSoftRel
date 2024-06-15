module com.example.relazioneingdelsoft {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires java.sql;

    opens com.example.relazioneingdelsoft to javafx.fxml;
    exports com.example.relazioneingdelsoft;
}