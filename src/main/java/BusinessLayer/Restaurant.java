package BusinessLayer;

import DataLayer.FileWriter;
import DataLayer.RestaurantSerializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * @invariant isWellFormed()
 */

public class Restaurant extends Observable implements IRestaurantProcessing {
    private HashMap<Order, ArrayList<MenuItem>> orders;
    private ArrayList<MenuItem> items;
    private ArrayList<Order> ordersList;

    public Restaurant() {
        orders = new HashMap<>();
        ordersList = new ArrayList<>();
        RestaurantSerializer serializer = new RestaurantSerializer();
        items = serializer.deserializateItem();
        if (items == null){
            items = new ArrayList<>();
        }
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    private boolean isWellFormed(){
        for (Order order : orders.keySet()){
            if (orders.get(order) == null){
                return false;
            }
        }
        if (items == null){
            return false;
        }
        for (MenuItem menuItem : items){
            if (menuItem instanceof CompositeProduct &&
                    ((CompositeProduct)menuItem).getProducts().size() < 2)
                return false;
        }
        return true;
    }

    private boolean containsId(int id){
        for (Order order : ordersList){
            if(order.getOrderId() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * @pre isWellFormed()
     * @pre menuItem != null && items != null
     * @pre menuItem instanceof BaseProduct ||
     *      menuItem instanceof CompositeProduct &&
     *      ((CompositeProduct)menuItem).getProducts().size() >= 2 : "Cannot add item!!";
     * @pre !items.contains(menuItem)
     * @post items.getSize() == items.getSize()@pre + 1
     * @post isWellFormed()
     */
    @Override
    public void createMenuItem(MenuItem menuItem) {
        assert isWellFormed() : "Something is wrong on restaurant";
        assert menuItem != null && !menuItem.getName().isEmpty() && items != null : "Cannot create item!!";
        assert  menuItem instanceof BaseProduct ||
                menuItem instanceof CompositeProduct &&
                        ((CompositeProduct)menuItem).getProducts().size() >= 2 : "You must to select more that one item for composite product!";
        assert !items.contains(menuItem) : "The item already exist";
        items.add(menuItem);
        assert isWellFormed() : "Something is wrong on restaurant";
    }

    /**
     * @pre isWellFormed()
     * @pre menuItem != null && items != null
     * @post isWellFormed()
     */
    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        assert isWellFormed() : "Something is wrong on restaurant";
        assert menuItem != null && items != null : "Cannot delete item!!";
        if (menuItem instanceof BaseProduct){
            ArrayList<MenuItem> itemsToDelete = new ArrayList<>();
            for (MenuItem item: items){
                if (item.containsItem((BaseProduct) menuItem)){
                    itemsToDelete.add(item);
                }
            }
            items.removeAll(itemsToDelete);
        }
        items.remove(menuItem);
        assert isWellFormed() : "Something is wrong on restaurant";
    }

    /**
     * Adauga un menuItem in lista de produse
     * @pre isWellFormed()
     * @pre i >= 0 && newName != null && newPrice > 0
     * @param i indicele produsului care trebuie modificat
     * @param newName noul nume al produsului
     * @param newPrice noul pret al produsului
     * @post isWellFormed()
     */
    @Override
    public void editMenuItem(int i, String newName, double newPrice) {
        assert isWellFormed() : "Something is wrong on restaurant";
        assert i >= 0 && newName != null && newPrice > 0 : "Cannot edit item";
        assert items != null : "Invalid data on fields!";
        MenuItem item = items.get(i);
        item.setName(newName);
        item.setPrice(newPrice);
        assert isWellFormed() : "Something is wrong on restaurant";
    }

    /**
     * @pre isWellFormed()
     * @pre !containsId(order.getOrderId()) && items != null
     * @post ordersList.getSize() == ordersList()@pre + 1
     * @post orders.getSize() == orders()@pre + 1
     * @post isWellFormed()
     * @param order noua comanda
     * @param items lista de produse de la noua comanda
     */
    @Override
    public void createOrder(Order order, ArrayList<MenuItem> items) {
        assert isWellFormed() : "Something is wrong on restaurant";
        assert !containsId(order.getOrderId()) && items != null : "Cannot add order!";
        orders.put(order, items);
        ordersList.add(order);
        setChanged();
        notifyObservers(items);
        assert isWellFormed() : "Something is wrong on restaurant";
    }

    /**
     * @pre i > 0
     * @param i indexul comenzii pentru care se calculeaza pretul
     */
    @Override
    public double computePriceForOrder(int i) {
        assert i >= 0 : "Cannot make the bill";
        double fullPrice = 0;
        Order order = ordersList.get(i);
        for (MenuItem item : orders.get(order)){
            fullPrice += item.getPrice();
        }
        return fullPrice;
    }

    /**
     * @pre ordersList.containsId(index)
     * @post @noChange
     * @param index indexul comenzii pentru care se face factura
     */
    @Override
    public void generateBill(int index) {
        Order order = ordersList.get(index);
        FileWriter.writeBill(order, orders);
    }

    public void update(){
        RestaurantSerializer serializer = new RestaurantSerializer();
        serializer.serializeItem(items);
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrders() {
        return orders;
    }

    public ArrayList<Order> getOrdersList() {
        return ordersList;
    }
}
