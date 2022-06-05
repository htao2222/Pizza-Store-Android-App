package PizzaPlace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private StoreOrders orders = new StoreOrders();
    private Order currentOrder;
    private String currentOrderNumber = null;
    String type;

    @FXML
    private TextField phoneNumber;

    @FXML
    void currentOrder(ActionEvent event) {
        if(currentOrderNumber == null){
            Alert orderAlert = new Alert(Alert.AlertType.WARNING);
            orderAlert.setTitle("Error!");
            orderAlert.setHeaderText("No order placed");
            orderAlert.setContentText("Please place an order first.");
            orderAlert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderView.fxml"));
            Parent root = (Parent) loader.load();
            OrderController orderView = loader.getController();
            orderView.setMainController(this);
            orderView.setPhone(getCurrentOrderNumber());
            Stage stage = new Stage();
            Scene scene = new Scene(root, 550, 650);
            stage.setTitle("Order View");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){

        }
    }

    @FXML
    void deluxe(ActionEvent event) {
        type = "Deluxe";
        callPizzaBuilder();
    }

    @FXML
    void hawaiian(ActionEvent event) {
        type = "Hawaii";
        callPizzaBuilder();
    }

    @FXML
    void pepperoni(ActionEvent event) {
        type = "Pepperoni";
        callPizzaBuilder();
    }

    @FXML
    void storeOrders(ActionEvent event) {
        if(orders.getPizzaOrders().size() <= 0){
            Alert orderAlert = new Alert(Alert.AlertType.WARNING);
            orderAlert.setTitle("Error!");
            orderAlert.setHeaderText("No orders placed");
            orderAlert.setContentText("Please place an order first.");
            orderAlert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreView.fxml"));
            Parent root = (Parent) loader.load();
            StoreController orderView = loader.getController();
            orderView.setMainController(this);
            orderView.setOrders(orders);
            Stage stage = new Stage();
            Scene scene = new Scene(root, 550, 650);
            stage.setTitle("Store View");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){

        }
    }

//    public String getType(){
//        return type;
//    }
//
//    public StoreOrders getOrders(){
//        return orders;
//    }

    public String getCurrentOrderNumber(){
        return currentOrderNumber;
    }

    public Order getCurrentOrder(){
        return currentOrder;
    }

    public void addOrder(Pizza tempPizza){
        currentOrder.addPizza(tempPizza);
    }

    public void placeOrder(Order tempOrder){
        orders.addOrder(tempOrder);
        currentOrderNumber = null;
    }

    public void removePizza(Pizza tempPizza){
        currentOrder.removePizza(tempPizza);
    }

    public void removeOrder(Order tempOrder){
        //System.out.println("removeOrder: " + orders.getPizzaOrders().size());
        orders.removeOrder(tempOrder);
        //System.out.println("removeOrder: " + orders.getPizzaOrders().size());
    }

    private void callPizzaBuilder(){
        if(!(phoneNumber.getText().matches("[0-9]+") && phoneNumber.getText().length() == 10)){
            Alert orderAlert = new Alert(Alert.AlertType.WARNING);
            orderAlert.setTitle("Error!");
            orderAlert.setHeaderText("Invalid Phone Number");
            orderAlert.setContentText("Please enter a valid phone number.");
            orderAlert.showAndWait();
            return;
        }
        //Maybe add another contains check instead
        if(orders.contains(new Order(phoneNumber.getText())) != -1){
            Alert orderAlert = new Alert(Alert.AlertType.WARNING);
            orderAlert.setTitle("Error!");
            orderAlert.setHeaderText("Phone Number already exists");
            orderAlert.setContentText("Please enter a valid phone number.");
            orderAlert.showAndWait();
            return;
        }
        if(currentOrderNumber == null || currentOrderNumber != phoneNumber.getText()){
            currentOrderNumber = phoneNumber.getText();
            currentOrder = new Order(phoneNumber.getText());
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaBuilderView.fxml"));
            Parent root = (Parent) loader.load();
            PizzaBuilderController pizzaView = loader.getController();
            pizzaView.setMainController(this);
            pizzaView.setPizza(type);
            Stage stage = new Stage();
            Scene scene = new Scene(root, 550, 650);
            stage.setTitle("Pizza Builder");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){

        }
    }
}
