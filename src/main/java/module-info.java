module com.example.enigma3r {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.enigma3r to javafx.fxml;
    exports com.example.enigma3r;
}