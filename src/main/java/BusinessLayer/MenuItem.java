package BusinessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract boolean containsItem(BaseProduct product);

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }

        if (!(obj instanceof MenuItem)){
            return false;
        }

        MenuItem menuItem = (MenuItem)obj;
        long price1 = Double.doubleToLongBits(price);
        long price2 = Double.doubleToLongBits(menuItem.price);
        return name.equals(menuItem.name) && price1 == price2;
    }
}
