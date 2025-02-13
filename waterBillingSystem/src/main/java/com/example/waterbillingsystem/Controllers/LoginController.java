package com.example.waterbillingsystem.Controllers;

import com.example.waterbillingsystem.SceneSwitch;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField loginUser;
    @FXML private PasswordField loginPass;
    @FXML private BorderPane loginPage;
    @FXML private Label message;
    @FXML private Circle dbStatus;
    @FXML private TextField loginPassVisible;
    @FXML private CheckBox showPasswordCheckBox;


    @FXML
    private void initialize() {
        // Check database is connected
        try {
            DatabaseConnectionController.initializeDB();
            // Dot turns Green
            dbStatus.setFill(Color.GREEN);
        } catch (SQLException e) {
            // Dot turns Red
            dbStatus.setFill(Color.RED);
        }

        // Bind the text of both fields together
        loginPassVisible.textProperty().bindBidirectional(loginPass.textProperty());

        // Toggle visibility based on CheckBox selection
        showPasswordCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                loginPassVisible.setVisible(true);
                loginPass.setVisible(false);
            } else {
                loginPassVisible.setVisible(false);
                loginPass.setVisible(true);
            }
        });
    }

    @FXML
    private void onUserLogin() throws IOException {
        String user = loginUser.getText();
        String pass = loginPass.getText();

        if (user.isEmpty() && pass.isEmpty()) {
            message.setText("Username or Password Required");
        } else if (user.isEmpty()) {
            message.setText("Username is required");
        } else if (pass.isEmpty()) {
            message.setText("Password is required");
        }
        else {
            try {
                ResultSet data = DatabaseConnectionController.loginVerification(loginUser.getText(), loginPass.getText());

                if (data.next()) {
                    // Redirect to dashboard page
                    String role = data.getString("Role");
                    SceneSwitch sceneSwitch = new SceneSwitch(loginPage, "dashboard-view.fxml");
                    DashboardController dashboardController = sceneSwitch.getController();
                    dashboardController.loginType(user,role);

                } else {
                    message.setText("Invalid username or password");
                }
            } catch (SQLException e) {
                message.setText("Login error: " + e.getMessage());
            }
        }
    }

}
