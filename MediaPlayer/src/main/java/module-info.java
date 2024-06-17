module lk.ijse.mediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens lk.ijse.mediaplayer to javafx.fxml;
    exports lk.ijse.mediaplayer;
}