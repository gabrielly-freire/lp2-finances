package br.ufrn.imd.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;
import br.ufrn.imd.service.BillService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ListBillsController {

    @FXML
    private TableView<Bill> billsTable;

    @FXML
    private TableColumn<Bill, String> descriptionColumn;

    @FXML
    private TableColumn<Bill, Double> valueColumn;

    @FXML
    private TableColumn<Bill, LocalDate> dueDateColumn;

    @FXML
    private TableColumn<Bill, LocalDate> paymentDateColumn;

    @FXML
    private TableColumn<Bill, Boolean> isPaidColumn;

    @FXML
    private TableColumn<Bill, Category> categoryColumn;

    @FXML
    private TableColumn<Bill, Void> actionsColumn;


    @FXML
    private Button registerBillButton;

    private BillService billService = new BillService();


    @FXML
    public void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Bill, Void> call(final TableColumn<Bill, Void> param) {
                return new TableCell<>() {
                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox actionButtons = new HBox(updateButton, deleteButton);

                    {
                        updateButton.setOnAction(event -> {
                            Bill bill = getTableView().getItems().get(getIndex());
                            billService.updateBill(bill.getId(), bill);
                        });

                        deleteButton.setOnAction(event -> {
                            Bill bill = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(bill);
                            billService.deleteBill(bill.getId());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });

        List<Bill> billsList = billService.findAllBills();
        ObservableList<Bill> billsObservable = FXCollections.observableArrayList(billsList);
        billsTable.setItems(billsObservable);

        registerBillButton.setOnAction(event -> openCreateBillScreen());
    }

    private void openCreateBillScreen() {
        try {
            App.setRoot("createBill.fxml");

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

}
