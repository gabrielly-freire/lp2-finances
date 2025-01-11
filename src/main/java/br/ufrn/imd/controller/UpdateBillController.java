package br.ufrn.imd.controller;

import java.io.IOException;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;
import br.ufrn.imd.service.BillService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class UpdateBillController {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField valueField;

    @FXML
    private DatePicker dueDataField;

    @FXML
    private DatePicker paymentDataField;

    @FXML
    private ToggleButton isPagoToggleButton;

    @FXML
    private ChoiceBox<Category> categoryChoiceBox;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    private Bill bill;
    private final BillService billService = new BillService();

    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().setAll(Category.values());

        isPagoToggleButton.setOnAction(event -> toggleIsPago());

        updateButton.setOnAction(event -> updateBill());
        cancelButton.setOnAction(event -> cancelUpdate());
    }

    public void setBill(Bill bill) {
        this.bill = bill;

        descriptionField.setText(bill.getDescription());
        valueField.setText(String.valueOf(bill.getValue()));
        dueDataField.setValue(bill.getDueDate());
        paymentDataField.setValue(bill.getPaymentDate());
        isPagoToggleButton.setText(String.valueOf(bill.getIsPaid()));
        isPagoToggleButton.setSelected(bill.getIsPaid());
        categoryChoiceBox.setValue(bill.getCategory());
    }

    public void updateBill() {
        try {
            bill.setDescription(descriptionField.getText());
            bill.setValue(Double.parseDouble(valueField.getText()));
            bill.setDueDate(dueDataField.getValue());
            bill.setPaymentDate(paymentDataField.getValue());
            bill.setIsPaid(isPagoToggleButton.isSelected());
            bill.setCategory(categoryChoiceBox.getValue());

            billService.updateBill(bill.getId(), bill);

            closeWindow();
            cancelUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Erro ao atualizar a fatura", e.getMessage());
        }
    }

    private void toggleIsPago() {
        boolean currentState = isPagoToggleButton.isSelected();
        isPagoToggleButton.setText(String.valueOf(currentState));
    }


    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void closeWindow() {
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
    }


    public void cancelUpdate() {
        try {
            App.setRoot("listBill.fxml");

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
