package com.example.waterbillingsystem.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AddCustomerController {
    @FXML private TextField FirstName;
    @FXML private TextField LastName;
    @FXML private TextField Email;
    @FXML private TextField Phone;
    @FXML private TextField Address;
    @FXML private TextField City;
    @FXML private TextField State;
    @FXML private TextField ZipCode;
    @FXML private TextField ServiceCenter;
    @FXML private Label addAlert;


    @FXML
    private void handleSave() {
        String firstName = FirstName.getText();
        String lastName = LastName.getText();
        String email = Email.getText();
        String phone = Phone.getText();
        String address = Address.getText();
        String city = City.getText();
        String state = State.getText();
        String zipCode = ZipCode.getText();
        String serviceCenter = ServiceCenter.getText();

//        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
//            addAlert.setText("Missing Fields! Please fill up.");
//            return;
//        }

        String sql = "INSERT INTO Customers (FirstName, LastName, Email, Phone, Address, City, State, ZipCode, ServiceCenter) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(sql);
        try {
            boolean data = DatabaseConnectionController.addCustomer(firstName, lastName, email, phone, address, city, state, zipCode, serviceCenter);

            if (data) {
                Stage stage = (Stage) FirstName.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Saving: " + FirstName.getText() + " " + LastName.getText());
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
