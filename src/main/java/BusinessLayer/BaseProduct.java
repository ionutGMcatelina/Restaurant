package BusinessLayer;

public class BaseProduct extends MenuItem {
    private String ingredient;


    public BaseProduct(String name, double price, String ingredient) {
        super(name, price);
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public boolean containsItem(BaseProduct product) {
        return false;
    }

    @Override
    public String toString() {
        return ingredient;
    }
}
