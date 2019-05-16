package DataLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.MenuItem;

import java.io.*;
import java.util.ArrayList;

public class RestaurantSerializer {

    public void serializeItem(ArrayList<MenuItem> menuItems){
        try{
            FileOutputStream fileOut = new FileOutputStream("items.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(menuItems);
            out.close();
            fileOut.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<MenuItem> deserializateItem(){
        ArrayList<MenuItem> menuItem = null;
        try{
            FileInputStream fileIn = new FileInputStream("items.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            menuItem = (ArrayList<MenuItem>)in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("EMPTY");
        }
        return menuItem;
    }
}
