module app.asteroids {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.google.gson;


    opens app to javafx.fxml;
    opens app.api to com.google.gson;
    exports app;
}