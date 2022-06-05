package PizzaPlace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PizzaBuilderController {

    private static final int MAX_TOPPINGS = 7;
    private MainController main = new MainController();
    private ObservableList<Topping> addToppings = FXCollections.observableArrayList();
    private ObservableList<Topping> pizzaToppings = FXCollections.observableArrayList();
    Pizza pizzaOrder;

    @FXML
    private ListView<Topping> additionalToppings;

    @FXML
    private ListView<Topping> currentToppings;

    @FXML
    private Button pizzaButton;

    @FXML
    private ComboBox<Size> pizzaSize;

    @FXML
    private ImageView pizzaView;

    @FXML
    private TextField price;

    @FXML
    void add(ActionEvent event) {
        if(additionalToppings.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No topping selected");
            alert.setContentText("Select a topping");
            alert.showAndWait();
            return;
        }
        if(addToppings.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Out of toppings");
            alert.setContentText("No more toppings to add");
            alert.showAndWait();
            return;
        }
        if(pizzaToppings.size() >= MAX_TOPPINGS){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Too many toppings");
            alert.setContentText("Cannot add any more toppings");
            alert.showAndWait();
            return;
        }
        pizzaToppings.add(addToppings.get(additionalToppings.getSelectionModel().getSelectedIndex()));
        boolean fail = true;
        if(pizzaOrder instanceof Deluxe){
            fail = ((Deluxe) pizzaOrder).addTopping(addToppings.get(additionalToppings.getSelectionModel().getSelectedIndex()));
        }
        if(pizzaOrder instanceof Hawaii){
            fail = ((Hawaii) pizzaOrder).addTopping(addToppings.get(additionalToppings.getSelectionModel().getSelectedIndex()));
        }
        if(pizzaOrder instanceof Pepperoni){
            fail = ((Pepperoni) pizzaOrder).addTopping(addToppings.get(additionalToppings.getSelectionModel().getSelectedIndex()));
        }
        if(!fail){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error");
            alert.setContentText("Error");
            alert.showAndWait();
            return;
        }
        addToppings.remove(additionalToppings.getSelectionModel().getSelectedIndex());
        price.setText(String.valueOf(pizzaOrder.price()));
    }

    @FXML
    void remove(ActionEvent event) {
        if(pizzaToppings.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Out of toppings");
            alert.setContentText("No more toppings to remove");
            alert.showAndWait();
            return;
        }
        if(currentToppings.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No topping selected");
            alert.setContentText("Select a topping");
            alert.showAndWait();
            return;
        }
        addToppings.add(pizzaToppings.get(currentToppings.getSelectionModel().getSelectedIndex()));
        boolean fail = true;
        if(pizzaOrder instanceof Deluxe){
            fail = ((Deluxe) pizzaOrder).removeTopping(pizzaToppings.get(currentToppings.getSelectionModel().getSelectedIndex()));
        }
        if(pizzaOrder instanceof Hawaii){
            fail = ((Hawaii) pizzaOrder).removeTopping(pizzaToppings.get(currentToppings.getSelectionModel().getSelectedIndex()));
        }
        if(pizzaOrder instanceof Pepperoni){
            fail = ((Pepperoni) pizzaOrder).removeTopping(pizzaToppings.get(currentToppings.getSelectionModel().getSelectedIndex()));
        }
        if(!fail){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error");
            alert.setContentText("Error");
            alert.showAndWait();
            return;
        }
        pizzaToppings.remove(currentToppings.getSelectionModel().getSelectedIndex());
        price.setText(String.valueOf(pizzaOrder.price()));
    }

    @FXML
    void orderButton(ActionEvent event) {
        main.addOrder(pizzaOrder);
    }

    @FXML
    void sizeChange(ActionEvent event) {
        if(pizzaOrder instanceof Deluxe){
            ((Deluxe) pizzaOrder).setSize(pizzaSize.getValue());
        }
        if(pizzaOrder instanceof Hawaii){
            ((Hawaii) pizzaOrder).setSize(pizzaSize.getValue());
        }
        if(pizzaOrder instanceof Pepperoni){
            ((Pepperoni) pizzaOrder).setSize(pizzaSize.getValue());
        }
        price.setText(String.valueOf(pizzaOrder.price()));
    }

    public void setMainController(MainController controller){
        main = controller;
    }

    public void setPizza(String type){
        Image pizza = new Image("file:src/main/resources/PizzaPlace/deluxepizza.jpg");
        pizzaOrder = PizzaMaker.createPizza(type);
        if(type.equals("Deluxe")){
            price.setText("12.99");
            addToppings.removeAll(Topping.Sausage, Topping.Peppers, Topping.Mushrooms, Topping.Beef);
            pizzaToppings.addAll(Topping.Sausage, Topping.Peppers, Topping.Mushrooms, Topping.Beef);
        }
        if(type.equals("Pepperoni")){
            pizza = new Image("file:src/main/resources/PizzaPlace/pepperonipizza.jpg");
            pizzaButton.setText("Pepperoni");
            price.setText("8.99");
        }
        if(type.equals("Hawaii")){
            pizza = new Image("file:src/main/resources/PizzaPlace/hawaiianpizza.jpg");
            pizzaButton.setText("Hawaii");
            price.setText("10.99");
            addToppings.removeAll(Topping.Ham, Topping.Pineapple);
            pizzaToppings.remove(Topping.Pepperoni);
            addToppings.add(Topping.Pepperoni);
            pizzaToppings.addAll(Topping.Pineapple, Topping.Ham);
        }
        pizzaView.setImage(pizza);
    }

    public void initialize() {
        pizzaSize.getItems().addAll(Size.Small, Size.Medium, Size.Large);
        pizzaSize.setValue(Size.Small);
        addToppings = FXCollections.observableArrayList(Topping.Pineapple, Topping.Ham, Topping.Sausage,
                Topping.Peppers, Topping.Mushrooms, Topping.Beef, Topping.Chicken, Topping.Olives);
        pizzaToppings = FXCollections.observableArrayList(Topping.Pepperoni);
        additionalToppings.setItems(addToppings);
        currentToppings.setItems(pizzaToppings);
    }
}
