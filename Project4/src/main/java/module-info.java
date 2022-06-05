module com.example.project4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens PizzaPlace to javafx.fxml;
    exports PizzaPlace;
}