package PizzaPlace;

import java.util.ArrayList;

public abstract class Pizza {
    public static final int MAX_TOPPINGS = 7;
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;
    public abstract double price();
}