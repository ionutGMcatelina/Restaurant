package BusinessLayer;

import java.util.Date;

public class Order {
    private int orderId;
    private Date date;
    private int table;

    public Order(int orderId, Date date, int table) {
        this.orderId = orderId;
        this.date = date;
        this.table = table;
    }

    @Override
    public int hashCode() {
        return orderId + date.getDate();
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public int getTable() {
        return table;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return false;
        }

        if (!(obj instanceof Order)){
            return false;
        }
        Order order = (Order)obj;
        return orderId == order.orderId &&
                date.equals(order.date) &&
                table == order.table;
    }
}
