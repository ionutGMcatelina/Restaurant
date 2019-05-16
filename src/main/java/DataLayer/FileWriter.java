package DataLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileWriter {
    public static void writeBill(Order order, HashMap<Order, ArrayList<MenuItem>> orders){
        try {
            PrintWriter writer = new PrintWriter("BillFor " + order.getOrderId() + ".txt", "UTF-8");

            writer.println("Date: " + order.getDate());
            writer.println();
            int total = 0;
            for (MenuItem product : orders.get(order)){
                writer.println(product.getName()
                        + " price " + product.getPrice() +
                        " RON");
                total += product.getPrice();
            }
            writer.println();
            writer.println("Total to pay: " + total + " RON");

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
}
