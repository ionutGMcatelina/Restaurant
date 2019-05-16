package PresentationLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class BigGUI extends JFrame {
    private JLabel title = new JLabel("RESTAURANT");
    private JButton waiter = new JButton("WAITER");
    private JButton admin = new JButton("ADMIN");

    public BigGUI(){
        title.setFont(new Font( "Calibri", Font.PLAIN, 40));
        waiter.setFont(new Font( "Calibri", Font.PLAIN, 20));
        admin.setFont(new Font( "Calibri", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);

        title.setBounds(195, 50, 250, 50);
        waiter.setBounds(200, 120, 200, 50);
        waiter.setBackground(new Color(0,0,139));
        waiter.setForeground(Color.WHITE);
        admin.setBounds(200, 200, 200, 50);
        admin.setBackground(new Color(0,0,139));
        admin.setForeground(Color.WHITE);

        ImageIcon imageIcon = new ImageIcon("waiter.gif");
        JLabel label = new JLabel(imageIcon);
        label.setBounds(100, 300, 400, 400);

        JPanel content = new JPanel();
        content.add(title);
        content.add(admin);
        content.add(waiter);
        content.setLayout(null);
        content.setBackground(new Color(0,128,128));
        content.add(label);

        this.setTitle("Restaurant");
        this.setContentPane(content);
        //this.setSize(600, 800);
        this.setBounds(450,10,600,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    JTable createItemsTable(ArrayList<MenuItem> items){
        MyModel myModel = new MyModel();
        String[] headers = {"NAME", "PRICE"};
        myModel.setColumnIdentifiers(headers);

        for (MenuItem item : items){
            Object[] data = new Object[2];
            data[0] = item.getName();
            data[1] = item.getPrice();
            myModel.addRow(data);
        }

        return new JTable(myModel);
    }

    JTable createOrdersTable(HashMap<Order, ArrayList<MenuItem>> orders){
        MyModel myModel = new MyModel();
        String[] headers = {"ID", "DATE", "TABLE"};
        myModel.setColumnIdentifiers(headers);

        for (Order order : orders.keySet()) {
            Object[] data = new Object[3];
            data[0] = order.getOrderId();
            data[1] = order.getDate();
            data[2] = order.getTable();
            myModel.addRow(data);
        }

        return new JTable(myModel);
    }

    public class MyModel extends DefaultTableModel {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    void addAdminButtonListener(ActionListener e){
        admin.addActionListener(e);
    }

    void addWaiterButtonListener(ActionListener e){
        waiter.addActionListener(e);
    }
}
