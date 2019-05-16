package BusinessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {
    private ArrayList<MenuItem> products;

    public CompositeProduct(String name, double price, ArrayList<MenuItem> products) {
        super(name, price);
        this.products = products;
    }

    ArrayList<MenuItem> getProducts() {
        return products;
    }

    @Override
    public boolean containsItem(BaseProduct product) {
        if (products.contains(product)){
            return true;
        }
        for (MenuItem menuItem : products){
            if (menuItem.containsItem(product)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CompositeProduct{" +
                "products=" + products +
                '}';
    }

    public String getTextProducts(){
        StringBuilder s = new StringBuilder();
        s.append(super.getName());
        s.append("\nIngredients:\n");
        for (MenuItem item : products){
            s.append("-");
            s.append(item.getName());
            s.append("\n");
        }
        return s.toString();
    }
}
