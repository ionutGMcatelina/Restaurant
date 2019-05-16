package PresentationLayer;

import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdministratorGUI extends JFrame {
    private JButton createMenuItem = new JButton("CREATE");
    private JButton editMenuItem = new JButton("EDIT");
    private JButton deleteMenuItem = new JButton("DELETE");
    private JPanel content = new JPanel();

    private JButton addBaseProduct = new JButton("BASE PRODUCT");
    private JButton addCompositeProduct = new JButton("COMPOSITE PRODUCT");

    private JLabel nameProduct = new JLabel("Product name");
    private JTextField nameProductTextField = new JTextField(25);
    private JLabel priceProduct = new JLabel("Product price");
    private JTextField priceProductTextField = new JTextField(25);
    private JPanel addPanel = new JPanel();

    private JTable itemsTable = new JTable();
    private JScrollPane scrollPane = new JScrollPane();
    private JTextArea ingredients = new JTextArea();

    private JButton save = new JButton("SAVE");

    public AdministratorGUI(){
        JLabel title = new JLabel("ADMIN", SwingConstants.CENTER);
        title.setFont(new Font( "Calibri", Font.PLAIN, 40));
        title.setBounds(150, 50, 300, 50);
        title.setForeground(Color.WHITE);

        JLabel question = new JLabel("Chose what you want to do:");
        question.setFont(new Font( "Calibri", Font.PLAIN, 20));
        question.setBounds(200, 100, 300, 40);
        question.setForeground(Color.WHITE);

        createMenuItem.setBounds(225, 150, 150, 40);
        createMenuItem.setBackground(new Color(0,0,139));
        createMenuItem.setForeground(Color.WHITE);
        createMenuItem.setFont(new Font( "Calibri", Font.PLAIN, 25));
        editMenuItem.setBounds(225, 200, 150, 40);
        editMenuItem.setFont(new Font( "Calibri", Font.PLAIN, 25));
        editMenuItem.setBackground(new Color(0,0,139));
        editMenuItem.setForeground(Color.WHITE);
        deleteMenuItem.setBounds(225, 250, 150, 40);
        deleteMenuItem.setFont(new Font( "Calibri", Font.PLAIN, 25));
        deleteMenuItem.setBackground(new Color(0,0,139));
        deleteMenuItem.setForeground(Color.WHITE);

        nameProductTextField.setBackground(new Color(176,196,222));
        priceProductTextField.setBackground(new Color(176,196,222));

        addBaseProduct.setFont(new Font( "Calibri", Font.PLAIN, 20));
        addBaseProduct.setBackground(new Color(0,0,139));
        addBaseProduct.setForeground(Color.WHITE);
        addCompositeProduct.setFont(new Font( "Calibri", Font.PLAIN, 20));
        addCompositeProduct.setBackground(new Color(0,0,139));
        addCompositeProduct.setForeground(Color.WHITE);
        save.setBackground(new Color(0,0,139));
        save.setForeground(Color.WHITE);

        nameProduct.setForeground(Color.WHITE);
        priceProduct.setForeground(Color.WHITE);

        addPanel.add(nameProduct);
        addPanel.add(nameProductTextField);
        addPanel.add(priceProduct);
        addPanel.add(priceProductTextField);
        addPanel.setOpaque(false);

        nameProduct.setFont(new Font( "Calibri", Font.PLAIN, 20));
        nameProductTextField.setFont(new Font( "Calibri", Font.PLAIN, 20));
        priceProduct.setFont(new Font( "Calibri", Font.PLAIN, 20));
        priceProductTextField.setFont(new Font( "Calibri", Font.PLAIN, 20));
        addPanel.setBounds(80, 350, 440, 200);
        addPanel.setLayout(new GridLayout(3, 2));

        content.add(title);
        content.add(question);
        content.add(createMenuItem);
        content.add(editMenuItem);
        content.add(deleteMenuItem);

        content.setBackground(new Color(0,128,128));
        content.setLayout(null);

        this.setTitle("Administrator");
        this.setContentPane(content);
        this.setSize(1400, 800);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    void changeToAddPanel(){

        if (content.getComponentCount() == 7) {
            content.add(addPanel);
        }

        if (addPanel.getComponentCount() == 5){
            addPanel.remove(save);
        }
        addPanel.add(addBaseProduct);
        addPanel.add(addCompositeProduct);

        addPanel.revalidate();
        content.revalidate();
        this.revalidate();
    }

    void addTable(JTable table){
        itemsTable = table;
        itemsTable.setFont(new Font("Calibri", Font.PLAIN, 20));
        itemsTable.setRowHeight(40);
        itemsTable.setBackground(Color.CYAN);

        JScrollPane ingScrollPane = new JScrollPane(ingredients);
        scrollPane.setOpaque(false);
        ingScrollPane.setOpaque(false);

        scrollPane.setBounds(600, 100, 300, 600);
        scrollPane.setViewportView(itemsTable);
        ingScrollPane.setBounds(950, 100, 300, 600);

        itemsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = itemsTable.getSelectedRow();
                    ingredients.setText(Controller.getItem(row));
                    ingredients.revalidate();
                }
                if (e.getClickCount() == 1 && addPanel.getComponentCount() == 5){
                    int index = itemsTable.getSelectedRow();
                    nameProductTextField.setText("" + itemsTable.getValueAt(index, 0));
                    priceProductTextField.setText("" + itemsTable.getValueAt(index, 1));
                }
            }
        });
        ingredients.setFont(new Font("Calibri", Font.PLAIN, 20));
        ingredients.setEditable(false);

        content.add(scrollPane);
        content.add(ingScrollPane);
        content.revalidate();
        this.revalidate();
    }

    void changeToEditPanel(){
        if (addPanel.getComponentCount() == 6) {
            addPanel.remove(addCompositeProduct);
            addPanel.remove(addBaseProduct);
        }
        addPanel.add(save);

        if (content.getComponentCount() == 7) {
            content.add(addPanel);
        }

        save.setFont(new Font("Calibri", Font.PLAIN, 20));

        addPanel.revalidate();
        content.revalidate();
        this.revalidate();
    }

    void addProductToTable(MenuItem item){
        Object[] data = new Object[2];
        data[0] = item.getName();
        data[1] = item.getPrice();
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
        model.addRow(data);
        itemsTable.revalidate();
    }

    JTable getItemsTable() {
        return itemsTable;
    }

    int[] getSelectedItems(){
        return itemsTable.getSelectedRows();
    }

    void addAddButtonListener(ActionListener e){
        createMenuItem.addActionListener(e);
    }

    void addEditButtonListener(ActionListener e){
        editMenuItem.addActionListener(e);
    }

    void addDeleteButtonListener(ActionListener e){
        deleteMenuItem.addActionListener(e);
    }

    void addBaseProduct(ActionListener e){
        addBaseProduct.addActionListener(e);
    }

    void addCompositeProduct(ActionListener e){
        addCompositeProduct.addActionListener(e);
    }

    void addExitListener(WindowAdapter w){
        this.addWindowListener(w);
    }

    void addSaveButtonListener(ActionListener e){
        save.addActionListener(e);
    }

    String getProductName(){
        return nameProductTextField.getText();
    }

    String getProductPrice(){
        return priceProductTextField.getText();
    }


}
