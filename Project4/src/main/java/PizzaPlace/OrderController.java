package PizzaPlace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class OrderController {

    private MainController main;
    private ObservableList<String> orders = FXCollections.observableArrayList();
    Order currentOrder;

    @FXML
    private TextField customerPhone;

    @FXML
    private ListView<String> orderView;

    @FXML
    private TextField subtotalText;

    @FXML
    private TextField taxText;

    @FXML
    private TextField totalText;

    @FXML
    private Button orderButton;

    @FXML
    void order(ActionEvent event) {
        if(orders.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No pizzas to order");
            alert.setContentText("Go order some pizzas");
            alert.showAndWait();
            return;
        }
        main.placeOrder(currentOrder);
        Stage stage = (Stage) orderButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void remove(ActionEvent event) {
        if(orders.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No orders left");
            alert.setContentText("No orders left to remove");
            alert.showAndWait();
            return;
        }
        if(orderView.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No order selected");
            alert.setContentText("Select an order");
            alert.showAndWait();
            return;
        }
        //System.out.println(orderView.getSelectionModel().getSelectedIndex());
        //System.out.println(orderView.getSelectionModel().getSelectedIndex());
        main.removePizza(currentOrder.getOrders().get(orderView.getSelectionModel().getSelectedIndex()));
        orders.remove(orderView.getSelectionModel().getSelectedIndex());
        subtotalText.setText(String.valueOf(currentOrder.totalCost()));
        taxText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 0.06625 * 100.0)/100.0));
        totalText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
    }

    public void setMainController(MainController controller){
        main = controller;
    }

    public void setPhone(String phone){
        //customerPhone.setText(phone);
        currentOrder = main.getCurrentOrder();
        //System.out.println(currentOrder.getOrders().size());
        for(int i = 0; i < currentOrder.getOrders().size(); i++){
            orders.add(currentOrder.getOrders().get(i).toString());
        }
        subtotalText.setText(String.valueOf(currentOrder.totalCost()));
        taxText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 0.06625 * 100.0)/100.0));
        totalText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
        customerPhone.setText(phone);
    }

    public void initialize() {
        orderView.setItems(orders);
    }

}
