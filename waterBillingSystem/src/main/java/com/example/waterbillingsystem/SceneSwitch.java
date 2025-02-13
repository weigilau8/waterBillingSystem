package com.example.waterbillingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;


public class SceneSwitch {
    private final FXMLLoader loader;

    public SceneSwitch(BorderPane currentPage, String fxml) throws IOException {
        loader = new FXMLLoader(WBSApplication.class.getResource(fxml));
        BorderPane nextBorderPane = loader.load();
        currentPage.getChildren().removeAll(nextBorderPane);
        currentPage.getChildren().setAll(nextBorderPane);
    }

    public <T> T getController() {
        return loader.getController();
    }
}
