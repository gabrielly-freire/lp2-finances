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
 * Controlador responsável pela tela do Dashboard.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class DashboardController {

    @FXML
    private PieChart categoryChart;

    @FXML
    private Button registerBillButton;

    private final BillService billService = new BillService();

    /**
     * Inicializa a tela de Dashboard.
     * Carrega os dados das categorias e configura a ação do botão.
     */
    @FXML
    public void initialize() {
        loadCategoryData();
        configureRegisterBillButton();
    }

    /**
     * Carrega os dados de faturas por categoria e exibe no gráfico de pizza.
     */
    private void loadCategoryData() {
        List<Bill> bills = billService.findAllBills();
        Map<Category, Double> categoryTotals = calculateCategoryTotals(bills);
        ObservableList<PieChart.Data> chartData = prepareChartData(categoryTotals);
        updateChart(chartData);
    }

    /**
     * Calcula o total de cada categoria com base nas faturas.
     * 
     * @param bills A lista de faturas.
     * @return Um mapa associando cada categoria ao total de valores.
     */
    private Map<Category, Double> calculateCategoryTotals(List<Bill> bills) {
        Map<Category, Double> categoryTotals = new HashMap<>();
        for (Bill bill : bills) {
            categoryTotals.merge(bill.getCategory(), bill.getValue(), Double::sum);
        }
        return categoryTotals;
    }

    /**
     * Prepara os dados para o gráfico de pizza com base nos totais por categoria.
     * 
     * @param categoryTotals O mapa com os totais por categoria.
     * @return Uma lista de dados para o gráfico de pizza.
     */
    private ObservableList<PieChart.Data> prepareChartData(Map<Category, Double> categoryTotals) {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        for (Map.Entry<Category, Double> entry : categoryTotals.entrySet()) {
            chartData.add(new PieChart.Data(entry.getKey().getDescription(), entry.getValue()));
        }
        return chartData;
    }

    /**
     * Atualiza o gráfico de categorias com os dados fornecidos.
     * 
     * @param chartData A lista de dados a ser exibida no gráfico.
     */
    private void updateChart(ObservableList<PieChart.Data> chartData) {
        categoryChart.setData(chartData);
        categoryChart.setTitle("Gastos por Categoria");
    }

    /**
     * Configura a ação do botão para registrar uma nova fatura.
     */
    private void configureRegisterBillButton() {
        registerBillButton.setOnAction(event -> registerBill());
    }

    /**
     * Navega para a tela de registro de fatura.
     */
    private void registerBill() {
        try {
            App.setRoot("createBill.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
