package com.example.waterbillingsystem.Controllers;

import com.example.waterbillingsystem.Bill;
import com.example.waterbillingsystem.Customer;
import com.example.waterbillingsystem.SceneSwitch;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.waterbillingsystem.Controllers.DatabaseConnectionController.*;
import static com.example.waterbillingsystem.Controllers.DatabaseConnectionController.getOverdueBills;

//import static sun.lwawt.macosx.CAccessibility.getChildren;


public class DashboardController {

    // Get userId data from LoginController
    private String loggedUser;
    private String loggedType;

    @FXML private Label theUserLogged;

    @FXML private PieChart dashPie;
    @FXML private Label custNumber;

    // Customer Tab
    @FXML private TableView customerTable;
    @FXML private TableColumn custCustomerID;
    @FXML private TableColumn custFirstName;
    @FXML private TableColumn custLastName;
    @FXML private TableColumn custEmail;
    @FXML private TableColumn custPhone;
    @FXML private TableColumn custAddress;
    @FXML private TableColumn custCity;
    @FXML private TableColumn custState;
    @FXML private TableColumn custZipCode;
    @FXML private TableColumn custServiceCenter;

    // Add customer
    @FXML private Button addCustomerButton;


    @FXML private Label totalPaid;
    @FXML private Label totalPending;
    @FXML private Label totalDue;


    // Bill Tab
    @FXML private TableView billTable;
    @FXML private TableColumn billId;
    @FXML private TableColumn billCustomerName;
    @FXML private TableColumn billDate;
    @FXML private TableColumn billDueDate;
    @FXML private TableColumn billUnitConsumed;
    @FXML private TableColumn billRatePerUnit;
    @FXML private TableColumn billFixedCharge;
    @FXML private TableColumn billStatus;


    @FXML private BorderPane dashboardPage;

    public void loginType(String loggedUser,String role) throws SQLException {
        this.loggedUser = loggedUser;
        this.loggedType = role;

        theUserLogged.setText(loggedUser);

        if ("Admin".equals(this.loggedType)) {
            initializeColumns();
            loadCustomerData();
            loadBillData();

        }
    }

    @FXML
    private void initialize() throws SQLException {
        loadPieChart();
        loadTotalCustomers();
    }

    private void loadTotalCustomers() throws SQLException{
        custNumber.setText(String.valueOf(getTotalCustomers()));
    }

    private void loadPieChart() throws SQLException {

        int thePending = getPendingBills();
        int thePaid = getPaidBills();
        int theOverdue = getOverdueBills();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Paid", thePaid),
                new PieChart.Data("Pending", thePending),
                new PieChart.Data("Over due", theOverdue)
        );

        dashPie.getData().addAll(pieChartData);

    }

    private void initializeColumns() {
        custCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        custFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        custLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        custEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        custPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        custCity.setCellValueFactory(new PropertyValueFactory<>("City"));
        custState.setCellValueFactory(new PropertyValueFactory<>("State"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("ZipCode"));
        custServiceCenter.setCellValueFactory(new PropertyValueFactory<>("ServiceCenter"));

        // This to create number
        billId.setCellFactory(col -> new TableCell<Bill, Integer>() {

            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1)); // Auto generate numberid
                }
            }
        });

        billCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        billDate.setCellValueFactory(new PropertyValueFactory<>("BillDate"));
        billDueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        billUnitConsumed.setCellValueFactory(new PropertyValueFactory<>("UnitsConsumed"));
        billRatePerUnit.setCellValueFactory(new PropertyValueFactory<>("RatePerUnit"));
        billFixedCharge.setCellValueFactory(new PropertyValueFactory<>("FixedCharge"));
        billStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

    }

    private void loadCustomerData() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        ResultSet rs = DatabaseConnectionController.getAllCustomers();

        while (rs.next()) {
            customerList.add(new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("City"),
                    rs.getString("State"),
                    rs.getString("ZipCode"),
                    rs.getString("ServiceCenter")
            ));
        }

        customerTable.setItems(customerList);
    }

    private void loadBillData() throws SQLException {
        ObservableList<Bill> billList = FXCollections.observableArrayList();
        ResultSet rs = (ResultSet) DatabaseConnectionController.getAllBills();

        while (rs.next()) {
            billList.add(new Bill(
                    rs.getInt("CustomerID"),
                    rs.getString("CustomerName"),
                    rs.getString("BillDate"),
                    rs.getString("DueDate"),
                    rs.getInt("UnitsConsumed"),
                    rs.getDouble("RatePerUnit"),
                    rs.getDouble("FixedCharge"),
                    rs.getString("Status")
            ));
        }
        billTable.setItems(billList);


        totalPaid.setText(String.valueOf(getPaidBills()));
        totalPending.setText(String.valueOf(getPendingBills()));
        totalDue.setText(String.valueOf(getOverdueBills()));


    }

    @FXML void onAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/waterbillingsystem/addCustomer-view.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Add Customer");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.showAndWait();
            refreshCustomerTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCustomerTable() {
        initializeColumns();
        try {
            loadCustomerData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() throws IOException {
        loggedType = null;
        new SceneSwitch(dashboardPage, "login-view.fxml");
    }


}
