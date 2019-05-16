package PresentationLayer;

import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI extends JFrame implements Observer {
    private JTextArea food = new JTextArea();

    ChefGUI(){
        food.setFont(new Font("Calibri", Font.PLAIN, 20));
        food.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(food);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        scrollPane.setBounds(500, 20, 300, 400);

        ImageIcon imageIcon = new ImageIcon("bucatar.gif");
        JLabel label = new JLabel(imageIcon);
        label.setBounds(20, 50, 400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(scrollPane);
        panel.add(label);
        panel.setBackground(new Color(0,128,128));

        this.pack();
        this.setContentPane(panel);
        this.setSize(850, 500);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<MenuItem> items = (ArrayList<MenuItem>)arg;
        food.setText("");
        for (MenuItem item : items){
            if (item instanceof CompositeProduct){
                food.append("- ");
                food.append(item.getName());
                food.append("\n");
            }
        }
        food.revalidate();
        this.revalidate();
    }
}
