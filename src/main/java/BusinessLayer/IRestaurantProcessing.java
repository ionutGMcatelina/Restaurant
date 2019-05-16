package BusinessLayer;

import java.util.ArrayList;

public interface IRestaurantProcessing {
    void createMenuItem(MenuItem menuItem);
    void deleteMenuItem(MenuItem menuItem);
    void editMenuItem(int i, String newName, double newPrice);
    void createOrder(Order order, ArrayList<MenuItem> items);
    double computePriceForOrder(int i);
    void generateBill(int index);
}
