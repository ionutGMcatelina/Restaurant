import BusinessLayer.BaseProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;
import PresentationLayer.*;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        AdministratorGUI administratorGUI = new AdministratorGUI();

        WaiterGUI waiterGUI = new WaiterGUI();

        BigGUI bigGUI = new BigGUI();
        bigGUI.setVisible(true);
        Controller controller = new Controller(administratorGUI, bigGUI, waiterGUI);

        /*Date date = new Date();
        System.out.println(date.getMonth());*/
    }


}
