package com.nicodangelo.pantrie.item;

/**
 * Created by Jetts on 3/28/2015.
 */
import java.util.ArrayList;

public class ItemController {
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static int curSpot;

    public ItemController() {
    }

    public void printAll() {
        for (int k = 0; k < curSpot; k++) {
            System.out.print(items.get(k).getName());
        }
    }

    public void addItem(String s) {
        Item e = new Item(s);
        items.add(e);
        curSpot++;
    }

    public void setAmount(int i, int spot) {

        items.get(i).setAmount(spot);
    }

    public int getAmount(int spot) { return items.get(spot).getAmount(); }

    public String getName(int spot) {
        return "Item Name: " + items.get(spot).getName();
    }

    public void setLowAmount(int spot, int low) {
        items.get(spot).setLow(low);
    }

    public int getLowAmount(int spot) { return items.get(spot).getLow();}

    public int getSpot() {
        return curSpot;
    }

    public void setType(int spot, String type) {
        items.get(spot).setType(type);
    }

    public String getType(int spot) { return items.get(spot).getType(); }

    public void setMes(int spot, String mes)
    {
        items.get(spot).setMeasurement(mes);
    }

    public String getMes(int spot) { return items.get(spot).getMeasurment(); }

    public String getInfo(int spot)
    {
        return "Item Name: " + items.get(spot).getName() + " Amount: " + items.get(spot).getAmount();
    }

}
