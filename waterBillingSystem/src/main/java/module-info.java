module com.example.waterbillingsystem {
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires com.gluonhq.charm.glisten;

    opens com.example.waterbillingsystem to javafx.fxml;
    exports com.example.waterbillingsystem;
    exports com.example.waterbillingsystem.Controllers;
    opens com.example.waterbillingsystem.Controllers to javafx.fxml;
}