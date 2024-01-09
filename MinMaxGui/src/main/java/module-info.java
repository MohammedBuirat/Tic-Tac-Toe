module com.example.minmaxgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minmaxgui to javafx.fxml;
    exports com.example.minmaxgui;
}