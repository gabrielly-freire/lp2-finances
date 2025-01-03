package br.ufrn.imd.controller;

import java.io.IOException;
import java.time.LocalDate;

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

public class CreateBillController {
    
    @FXML
    private ChoiceBox<Category> categoryChoiceBox; 

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dueDataField;

    @FXML
    private ToggleButton isPagoToggleButton;

    @FXML
    private DatePicker paymentDataField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField valueField;

    private BillService billService = new BillService();

    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().setAll(Category.values());
    }

    @FXML
    public void createBill() {
        String description = descriptionField.getText();
        LocalDate dueData = dueDataField.getValue();
        LocalDate paymentData = paymentDataField.getValue();
        Category category = categoryChoiceBox.getValue();
        boolean isPago = isPagoToggleButton.isSelected();

        Double value;
        try {
            value = Double.parseDouble(valueField.getText());
        } catch (NumberFormatException e) {
            showError("Valor inválido", "O campo 'Value' deve conter um número válido.");
            return;
        }

        if (description.isEmpty() || dueData == null || category == null) {
            showError("Campos obrigatórios", "Preencha todos os campos obrigatórios.");
            return;
        }

        Bill bill = new Bill(null, description, value, dueData, paymentData, isPago, category);

        submitButton.setOnAction(e -> {
            System.out.println("Controller "+bill.toString());
            billService.createBill(bill);
            showInfo("Sucesso", "Fatura criada com sucesso!");
            openListBillsScreen();
        });

    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openListBillsScreen() {
        try {
            App.setRoot("listBill.fxml");

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
