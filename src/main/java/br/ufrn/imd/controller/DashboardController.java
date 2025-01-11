package br.ufrn.imd.controller;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;
import br.ufrn.imd.service.BillService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador da tela de Dashboard.
 */
public class DashboardController {

    @FXML
    private PieChart categoryChart;

    @FXML
    private Button registerBillButton;

    private final BillService billService = new BillService();

    @FXML
    public void initialize() {
        loadCategoryData();

        registerBillButton.setOnAction(event -> registerBill());
    }

    private void loadCategoryData() {
        List<Bill> bills = billService.findAllBills();

        Map<Category, Double> categoryTotals = new HashMap<>();
        for (Bill bill : bills) {
            categoryTotals.merge(bill.getCategory(), bill.getValue(), Double::sum);
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        for (Map.Entry<Category, Double> entry : categoryTotals.entrySet()) {
            chartData.add(new PieChart.Data(entry.getKey().getDescription(), entry.getValue()));
        }

        categoryChart.setData(chartData);
        categoryChart.setTitle("Gastos por Categoria");

    }

    private void registerBill() {
        try {
            App.setRoot("createBill.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
