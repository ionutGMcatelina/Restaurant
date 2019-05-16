package PresentationLayer;

import BusinessLayer.*;
import DataLayer.RestaurantSerializer;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class Controller {
    private AdministratorGUI administratorGUI;
    private BigGUI bigGUI;
    private WaiterGUI waiterGUI;
    private static Restaurant restaurant = new Restaurant();
    private ChefGUI chefGUI = new ChefGUI();

    public Controller(AdministratorGUI administratorGUI, BigGUI bigGUI, WaiterGUI waiterGUI) {
        this.administratorGUI = administratorGUI;
        this.bigGUI = bigGUI;
        this.waiterGUI = waiterGUI;

        restaurant.addObserver(chefGUI);

        administratorGUI.addAddButtonListener(new AddItemButtonListener());
        administratorGUI.addBaseProduct(new AddBaseButtonListener());
        administratorGUI.addCompositeProduct(new AddCompositeButtonListener());
        administratorGUI.addDeleteButtonListener(new DeleteActionListener());
        administratorGUI.addExitListener(new ExitListener());
        administratorGUI.addEditButtonListener(new EditButtonListener());
        administratorGUI.addSaveButtonListener(new SaveActionListener());

        waiterGUI.addAddButtonListener(new CreateOrderListener());
        waiterGUI.addOrderButtonListener(new AddOrderListener());
        waiterGUI.addBillButtonListener(new GenerateBillListener());
        waiterGUI.addPriceButtonListener(new ComputePriceListener());

        bigGUI.addAdminButtonListener(new AdminButtonListener());
        bigGUI.addWaiterButtonListener(new WaiterButtonListener());
    }

    static String getItem(int i){
        MenuItem item = restaurant.getItems().get(i);
        if (item instanceof CompositeProduct){
            return ((CompositeProduct)item).getTextProducts();
        }
        return item.getName();
    }

    static String getOrder(int i){
        Order order = restaurant.getOrdersList().get(i);
        StringBuilder string = new StringBuilder();
        for (MenuItem item : restaurant.getOrders().get(order)){
            string.append("- ");
            string.append(item.getName());
            string.append("\n");
        }
        return string.toString();
    }

    class AddItemButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorGUI.changeToAddPanel();
            administratorGUI.setSize(1401, 800);
            administratorGUI.setSize(1400, 800);
        }
    }

    class AddBaseButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = administratorGUI.getProductName();
                double price = Double.parseDouble(administratorGUI.getProductPrice());

                MenuItem item = new BaseProduct(name, price, name);
                restaurant.createMenuItem(item);
                administratorGUI.addProductToTable(item);
            } catch (Error error){
                showMessage(error.getMessage());
            } catch (Exception ex){
                showMessage("Invalid name or price!");
            }
        }
    }

    class AddCompositeButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String name = administratorGUI.getProductName();
                double price = Double.parseDouble(administratorGUI.getProductPrice());

                ArrayList<MenuItem> items = new ArrayList<>();
                int[] v = administratorGUI.getSelectedItems();
                for (int x : v){
                    items.add(restaurant.getItems().get(x));
                }

                MenuItem item = new CompositeProduct(name, price, items);
                System.out.println(item);
                restaurant.createMenuItem(item);
                administratorGUI.addProductToTable(item);
            } catch (Error error){
                showMessage(error.getMessage());
            } catch (Exception ex){
                showMessage("Invalid name or price!");
            }
        }
    }

    class SaveActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int i = administratorGUI.getItemsTable().getSelectedRow();
                String newName = administratorGUI.getProductName();
                double newPrice = Double.parseDouble(administratorGUI.getProductPrice());
                restaurant.editMenuItem(i, newName, newPrice);
                JTable table = bigGUI.createItemsTable(restaurant.getItems());
                administratorGUI.addTable(table);
            } catch (Error error){
                showMessage(error.getMessage());
            } catch (Exception ex){
                showMessage("Invalid name or price!");
            }
        }
    }

    class DeleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int x = administratorGUI.getItemsTable().getSelectedRow();
            MenuItem item = restaurant.getItems().get(x);
            restaurant.deleteMenuItem(item);
            JTable table = bigGUI.createItemsTable(restaurant.getItems());
            administratorGUI.addTable(table);
            //administratorGUI.deleteFromTable(x);
        }
    }

    class ExitListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            restaurant.update();
        }
    }

    class EditButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorGUI.changeToEditPanel();
            administratorGUI.setSize(1401, 800);
            administratorGUI.setSize(1400, 800);
        }
    }

    class CreateOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            waiterGUI.changeToAddPanel();
        }
    }

    class AddOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(waiterGUI.getOrderId());
                Date date = new Date();
                int table = Integer.parseInt(waiterGUI.getOrderTable());
                ArrayList<MenuItem> items = new ArrayList<>();
                int[] selectedItems = waiterGUI.getSelectedItems();

                Order order = new Order(id, date, table);
                for (int x : selectedItems) {
                    items.add(restaurant.getItems().get(x));
                }
                restaurant.createOrder(order, items);
                waiterGUI.addOrderToTable(order);
            } catch (Error error){
                showMessage("The order already exists!");
            } catch (Exception ex){
                showMessage("Invalid id or table");
            }
        }
    }

    class ComputePriceListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                double price;
                int i = waiterGUI.getOrdersTable().getSelectedRow();
                price = restaurant.computePriceForOrder(i);
                showMessage("Price: " + price);
            } catch (Error er){
                showMessage("Cannot show the price!");
            }
        }
    }

    class GenerateBillListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = waiterGUI.getOrdersTable().getSelectedRow();
            restaurant.generateBill(index);
        }
    }

    class AdminButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = bigGUI.createItemsTable(restaurant.getItems());
            administratorGUI.addTable(table);
            administratorGUI.setVisible(true);
        }
    }

    class WaiterButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = bigGUI.createItemsTable(restaurant.getItems());
            JTable ordersTable = bigGUI.createOrdersTable(restaurant.getOrders());
            waiterGUI.addTable(ordersTable, table);
            waiterGUI.setVisible(true);
            chefGUI.setVisible(true);
        }
    }

    void showMessage(String message){
        JOptionPane.showMessageDialog(waiterGUI, message);
    }
}
