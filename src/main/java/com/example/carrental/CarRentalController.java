package com.example.carrental;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarRentalController {
    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<String> carTypeComboBox;
    @FXML
    private TextField amountPaidField;
    @FXML
    private DatePicker rentDatePicker;
    @FXML
    private DatePicker returnDatePicker;

    private ObservableList<Customer> customers;

    @FXML
    private void initialize() {
        customers = FXCollections.observableArrayList();
        tableView.setItems(customers);

        // Initialize table columns and other UI setup
        // ...

        carTypeComboBox.getItems().addAll("Sedan", "SUV", "Sports Car", "Family Car", "Bus");

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                addressField.setText(newSelection.getAddress());
                carTypeComboBox.setValue(newSelection.getCarType());
                amountPaidField.setText(String.valueOf(newSelection.getAmountPaid()));
                rentDatePicker.setValue(newSelection.getRentDate());
                returnDatePicker.setValue(newSelection.getReturnDate());
            }
        });
    }

    @FXML
    private void addCustomer(ActionEvent event) {
        if (isInputValid()) {
            String name = nameField.getText();
            String address = addressField.getText();
            String carType = carTypeComboBox.getValue();
            double amountPaid = Double.parseDouble(amountPaidField.getText());
            LocalDate rentDate = rentDatePicker.getValue();
            LocalDate returnDate = returnDatePicker.getValue();

            Customer customer = new Customer(name, address, carType, amountPaid, rentDate, returnDate);

            tableView.getItems().add(customer);

            clearInputFields();
            saveCustomers(); // Save the customers after adding
        }
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
        Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null && isInputValid()) {
            String name = nameField.getText();
            String address = addressField.getText();
            String carType = carTypeComboBox.getValue();
            double amountPaid = Double.parseDouble(amountPaidField.getText());
            LocalDate rentDate = rentDatePicker.getValue();
            LocalDate returnDate = returnDatePicker.getValue();

            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setCarType(carType);
            selectedCustomer.setAmountPaid(amountPaid);
            selectedCustomer.setRentDate(rentDate);
            selectedCustomer.setReturnDate(returnDate);

            tableView.refresh();
            saveCustomers(); // Save the customers after updating
            clearInputFields();
        }
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errorMessage.append("Name is required.\n");
        }
        if (addressField.getText().isEmpty()) {
            errorMessage.append("Address is required.\n");
        }
        if (carTypeComboBox.getValue() == null) {
            errorMessage.append("Car type is required.\n");
        }
        if (amountPaidField.getText().isEmpty()) {
            errorMessage.append("Amount paid is required.\n");
        } else {
            try {
                Double.parseDouble(amountPaidField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Amount paid must be a valid number.\n");
            }
        }
        if (rentDatePicker.getValue() == null) {
            errorMessage.append("Rent date is required.\n");
        }
        if (returnDatePicker.getValue() == null) {
            errorMessage.append("Return date is required.\n");
        }

        if (errorMessage.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private void saveCustomers() {
        try {
            List<Customer> customerList = new ArrayList<>(tableView.getItems());

            // Create a new file output stream
            FileOutputStream fileOutputStream = new FileOutputStream("customers.ser");

            // Create an object output stream using the file output stream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write the customer list to the object output stream
            objectOutputStream.writeObject(customerList);

            // Close the object output stream
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeleteAlert.setTitle("Confirmation");
            confirmDeleteAlert.setHeaderText(null);
            confirmDeleteAlert.setContentText("Are you sure you want to delete this customer?");

            ButtonType deleteButton = new ButtonType("Delete");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmDeleteAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            confirmDeleteAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == deleteButton) {
                    customers.remove(selectedIndex);
                    clearInputFields();
                }
            });
        }
    }

    @FXML
    private void clearInputFields() {
        nameField.clear();
        addressField.clear();
        carTypeComboBox.getSelectionModel().clearSelection();
        amountPaidField.clear();
        rentDatePicker.setValue(null);
        returnDatePicker.setValue(null);
    }
}
