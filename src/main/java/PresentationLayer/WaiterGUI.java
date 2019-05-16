package PresentationLayer;

import BusinessLayer.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class WaiterGUI extends JFrame {
    private JButton createOrder = new JButton("CREATE");
    private JButton computePrice = new JButton("COMPUTE PRICE");
    private JButton generateBill = new JButton("GENERATE BILL");
    private JPanel content = new JPanel();

    private JButton addOrder = new JButton("ADD");

    private JLabel orderId = new JLabel("Order ID");
    private JTextField orderIdTextField = new JTextField(25);
    private JLabel orderTable = new JLabel("Order table");
    private JTextField orderTableTextField = new JTextField();
    private JPanel addPanel = new JPanel();

    private JTable itemsTable = new JTable();
    private JTable ordersTable = new JTable();

    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane itemsScrollPane = new JScrollPane();

    private JTextArea itemsFromOrder = new JTextArea();

    public WaiterGUI(){
        JLabel title = new JLabel("WAITER", SwingConstants.CENTER);
        title.setFont(new Font( "Calibri", Font.PLAIN, 40));
        title.setBounds(150, 50, 300, 50);
        title.setForeground(Color.WHITE);

        JLabel question = new JLabel("Chose what you want to do:");
        question.setFont(new Font( "Calibri", Font.PLAIN, 20));
        question.setBounds(190, 100, 300, 40);
        question.setForeground(Color.WHITE);

        createOrder.setLayout(null);
        createOrder.setBounds(200, 150, 210, 40);
        createOrder.setFont(new Font( "Calibri", Font.PLAIN, 25));
        createOrder.setBackground(new Color(0,0,139));
        createOrder.setForeground(Color.WHITE);
        computePrice.setBounds(200, 200, 210, 40);
        computePrice.setFont(new Font( "Calibri", Font.PLAIN, 25));
        computePrice.setBackground(new Color(0,0,139));
        computePrice.setForeground(Color.WHITE);
        generateBill.setBounds(200, 250, 210, 40);
        generateBill.setFont(new Font( "Calibri", Font.PLAIN, 25));
        generateBill.setBackground(new Color(0,0,139));
        generateBill.setForeground(Color.WHITE);

        addOrder.setFont(new Font( "Calibri", Font.PLAIN, 20));
        addOrder.setBackground(new Color(0,0,139));
        addOrder.setForeground(Color.WHITE);

        orderIdTextField.setBackground(new Color(176,196,222));
        orderTableTextField.setBackground(new Color(176,196,222));
        orderId.setForeground(Color.WHITE);
        orderTable.setForeground(Color.WHITE);

        addPanel.add(orderId);
        addPanel.add(orderIdTextField);
        addPanel.add(orderTable);
        addPanel.add(orderTableTextField);
        orderId.setFont(new Font( "Calibri", Font.PLAIN, 20));
        orderIdTextField.setFont(new Font( "Calibri", Font.PLAIN, 20));
        orderTable.setFont(new Font( "Calibri", Font.PLAIN, 20));
        orderTableTextField.setFont(new Font( "Calibri", Font.PLAIN, 20));
        addPanel.setBounds(100, 350, 420, 200);
        addPanel.setLayout(new GridLayout(3, 2));
        addPanel.setOpaque(false);

        content.add(title);
        content.add(question);
        content.add(createOrder);
        content.add(computePrice);
        content.add(generateBill);

        JScrollPane itemsFromOrderS = new JScrollPane(itemsFromOrder);
        itemsFromOrderS.setBounds(100, 570, 420, 150);
        itemsFromOrder.setFont(new Font("Calibri", Font.PLAIN, 20));

        content.add(itemsFromOrderS);
        content.setBackground(new Color(0,128,128));
        content.setLayout(null);

        this.setTitle("Waiter");
        this.setContentPane(content);
        this.setSize(1400, 800);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    void changeToAddPanel(){
        content.add(addPanel);

        addPanel.add(addOrder);

        addPanel.revalidate();
        content.revalidate();
        this.revalidate();
    }

    void addTable(final JTable orderTable, JTable itemTable){
        this.itemsTable = itemTable;
        itemsTable.setFont(new Font("Calibri", Font.PLAIN, 20));
        itemsTable.setRowHeight(40);
        itemsTable.setBackground(Color.CYAN);
        itemsTable.setGridColor(Color.BLACK);
        this.ordersTable = orderTable;
        this.ordersTable.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.ordersTable.setRowHeight(40);
        this.ordersTable.setBackground(Color.CYAN);
        this.ordersTable.setGridColor(Color.BLACK);

        scrollPane.setBounds(600, 100, 300, 600);
        scrollPane.setViewportView(this.ordersTable);

        itemsScrollPane.setBounds(950, 100, 300, 600);
        itemsScrollPane.setViewportView(this.itemsTable);

        ordersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = ordersTable.getSelectedRow();
                    itemsFromOrder.setText(Controller.getOrder(row));
                    itemsFromOrder.revalidate();
                }
            }
        });

        content.add(scrollPane);
        content.add(itemsScrollPane);
        content.revalidate();
        this.revalidate();
    }

    void addOrderToTable(Order order){
        Object[] data = new Object[3];
        data[0] = order.getOrderId();
        data[1] = order.getDate();
        data[2] = order.getTable();
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.addRow(data);
        orderTable.revalidate();
    }

    int[] getSelectedItems(){
        return itemsTable.getSelectedRows();
    }

    void addAddButtonListener(ActionListener e){
        createOrder.addActionListener(e);
    }

    void addPriceButtonListener(ActionListener e){
        computePrice.addActionListener(e);
    }

    void addBillButtonListener(ActionListener e){
        generateBill.addActionListener(e);
    }

    void addOrderButtonListener(ActionListener e){
        addOrder.addActionListener(e);
    }

    String getOrderId(){
        return orderIdTextField.getText();
    }

    String getOrderTable(){
        return orderTableTextField.getText();
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }
}
