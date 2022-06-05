package PizzaPlace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StoreController {

    private MainController main;
    StoreOrders orders;
    private ObservableList<String> phones = FXCollections.observableArrayList();
    //private ObservableList<String> orderDetails = FXCollections.observableArrayList();

    @FXML
    private TextField orderTotalText;

    @FXML
    private ComboBox<String> phoneBox;

    @FXML
    private TextArea storeOrdersView;

    @FXML
    void cancel(ActionEvent event) {
        if(phones.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No orders left");
            alert.setContentText("Cannot remove");
            alert.showAndWait();
            return;
        }
        orders.removeOrder(new Order(phoneBox.getValue()));
        main.removeOrder(new Order(phoneBox.getValue()));
        phones.remove(phoneBox.getValue());
        //phoneBox.getItems().clear();
        phoneBox.getItems().remove(phoneBox.getValue());
//        for(int i = 0; i < orders.getPizzaOrders().size(); i++){
//            phones.add(orders.getPizzaOrders().get(i).getPhone());
//        }
        //phoneBox.getItems().remove(phoneBox.getValue());
        storeOrdersView.clear();
        orderTotalText.clear();
    }

    @FXML
    void export(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File targetFile = chooser.showSaveDialog(stage);
        try {
            orders.export(targetFile);
        } catch(Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error!");
//            alert.setHeaderText("IOException");
//            alert.setContentText("File path Error!");
//            alert.showAndWait();
            return;
        }
    }

    @FXML
    void numberChange(ActionEvent event) {
        if(phoneBox.getValue() == null){
            return;
        }
        int index = orders.contains(new Order(phoneBox.getValue()));
        Order currentOrder = orders.getPizzaOrders().get(index);
        storeOrdersView.setText(currentOrder.toString());
        orderTotalText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
    }

    public void setOrders(StoreOrders pizzaOrders){
        Order currentOrder;
        orders = pizzaOrders;
        for(int i = 0; i < orders.getPizzaOrders().size(); i++){
            phones.add(orders.getPizzaOrders().get(i).getPhone());
        }
        phoneBox.setItems(phones);
        phoneBox.setValue(orders.getPizzaOrders().get(0).getPhone());
        currentOrder = orders.getPizzaOrders().get(0);
        storeOrdersView.setText(currentOrder.toString());
        orderTotalText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
    }

    public void setMainController(MainController controller){
        main = controller;
    }

    public void initialize(){

    }

}
