package pizza.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pizza.app.database.PizzaOrderDatabase;
import pizza.app.models.Controller;
import pizza.app.models.Order;

import java.util.Map;
import java.util.Optional;

public class OrderController extends Controller {

    @FXML
    private TableView<Order> pizzaTable;
    @FXML
    private TableColumn<Order, String> tc_customerName, tc_mobileNumber, tc_pizzaSize;
    @FXML
    private TableColumn<Order, Integer> tc_numberOfToppings;
    @FXML
    private TableColumn<Order, Double> tc_totalBill;
    @FXML
    private TextField f_id, f_customerName, f_mobileNumber, f_numberOfToppings, f_totalBill;
    @FXML
    private CheckBox size_xl, size_l, size_m, size_s;
    private PizzaOrderDatabase db;
    private Order oldOrder;

    @Override
    public void init() {
        db = new PizzaOrderDatabase();
        tc_customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tc_mobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        tc_numberOfToppings.setCellValueFactory(new PropertyValueFactory<>("numberOfToppings"));
        tc_pizzaSize.setCellValueFactory(new PropertyValueFactory<>("pizzaSize"));
        tc_totalBill.setCellValueFactory(new PropertyValueFactory<>("totalBill"));
        pizzaTable.getSelectionModel().selectedItemProperty().addListener((obs, old, current) -> {
            if (current != null) {
                Order order = pizzaTable.getSelectionModel().getSelectedItem();
                if (order != null) {
                    oldOrder = order;
                    f_id.setText(String.valueOf(order.getOrderId()));
                    f_customerName.setText(order.getCustomerName());
                    f_mobileNumber.setText(order.getMobileNumber());
                    f_numberOfToppings.setText(order.getNumberOfToppings() + "");
                    size_xl.setSelected(false);
                    size_l.setSelected(false);
                    size_m.setSelected(false);
                    size_s.setSelected(false);
                    switch (order.getPizzaSize()){
                        case "XL":
                            size_xl.setSelected(true);
                            break;
                        case "L":
                            size_l.setSelected(true);
                            break;
                        case "M":
                            size_m.setSelected(true);
                            break;
                        case "S":
                            size_s.setSelected(true);
                            break;
                    }
                    f_totalBill.setText(String.valueOf(order.getTotalBill()));
                }
            }
        });
        oldOrder = null;
        updateOrders();
    }

    private void updateOrders() {
        Map<Integer, Order> orders = db.readOrders();
        ObservableList<Order> orderList = FXCollections.observableArrayList(orders.values());
        pizzaTable.setItems(orderList);
    }

    @FXML
    private void orderEvent(){
        String customerName = f_customerName.getText().trim();
        String mobileNumber = f_mobileNumber.getText().trim();
        String numberOfToppings = f_numberOfToppings.getText().trim();
        TextField[] fields = {f_customerName, f_mobileNumber, f_numberOfToppings};
        for (TextField field : fields) {
            if (field.getText().trim().isBlank()) {
                showAlert("Empty Field", "No any field should be empty!");
                return;
            }
        }
        int toppings;
        try {
            toppings = Integer.parseInt(numberOfToppings);
        } catch (NumberFormatException e) {
            showAlert("Incorrect data", "Toppings should have valid data.");
            return;
        }
        String size = null;
        if (size_xl.isSelected()){
            size = "XL";
        } else if (size_l.isSelected()){
            size = "L";
        } else if (size_m.isSelected()){
            size = "M";
        } else if (size_s.isSelected()){
            size = "S";
        }
        if (size == null){
            showAlert("No Size", "Select any single size variant!");
            return;
        }
        Order order = new Order(customerName, mobileNumber, size, toppings);
        order.calculate();
        db.insert(order);
        for (TextField field : fields) {
            field.clear();
        }
        size_xl.setSelected(false);
        size_l.setSelected(false);
        size_m.setSelected(false);
        size_s.setSelected(false);
        updateOrders();
    }

    @FXML
    private void updateEvent(){
        if (oldOrder == null) {
            showAlert("Update", "Select the row from table.");
            return;
        }
        String customerName = f_customerName.getText().trim();
        String mobileNumber = f_mobileNumber.getText().trim();
        String numberOfToppings = f_numberOfToppings.getText().trim();
        TextField[] fields = {f_customerName, f_mobileNumber, f_numberOfToppings};
        for (TextField field : fields) {
            if (field.getText().trim().isBlank()) {
                showAlert("Empty Field", "No any field should be empty!");
                return;
            }
        }
        int toppings;
        try {
            toppings = Integer.parseInt(numberOfToppings);
        } catch (NumberFormatException e) {
            showAlert("Incorrect data", "Toppings should have valid data.");
            return;
        }
        String size = null;
        if (size_xl.isSelected()){
            size = "XL";
        } else if (size_l.isSelected()){
            size = "L";
        } else if (size_m.isSelected()){
            size = "M";
        } else if (size_s.isSelected()){
            size = "S";
        }
        if (size == null){
            showAlert("No Size", "Select any single size variant!");
            return;
        }
        Order order = new Order(oldOrder.getOrderId(), customerName, mobileNumber, size, toppings, 0);
        double bill = order.calculate();
        f_totalBill.setText(String.format("%.2f", bill));
        db.update(oldOrder.getOrderId(), order);
        oldOrder = null;
        fields = new TextField[]{f_customerName, f_mobileNumber, f_numberOfToppings, f_totalBill, f_id};
        for (TextField field : fields) {
            field.clear();
        }
        size_xl.setSelected(false);
        size_l.setSelected(false);
        size_m.setSelected(false);
        size_s.setSelected(false);
        updateOrders();
    }

    @FXML
    private void deleteEvent(){
        if (oldOrder == null) {
            showAlert("Delete", "Select the row from table.");
            return;
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you confirmed to delete it?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            db.delete(oldOrder.getOrderId());
            oldOrder = null;
            updateOrders();
            TextField[] fields = {f_id, f_customerName, f_mobileNumber, f_numberOfToppings, f_totalBill};
            for (TextField field : fields) {
                field.clear();
            }
            showAlert("Done", "Order is deleted!");
        }
    }

}
