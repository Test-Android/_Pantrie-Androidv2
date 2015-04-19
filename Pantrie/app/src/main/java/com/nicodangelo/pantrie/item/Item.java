package com.nicodangelo.pantrie.item;

public class Item
{
    private String itemName;
    private String type;
    private String measurement;
    private int amountOfItem;
    private int warnLow;

    //Constructors
    public Item(String n, int a, int l, String t, String me)
    {
        itemName = n;
        amountOfItem = a;
        warnLow = l;
        type = t;
        measurement = me;
    }

    public Item(String name, int amount, int low)
    {
        itemName = name;
        amountOfItem = amount;
        warnLow = low;
    }

    public Item(String name, int amount)
    {
        itemName = name;
        amountOfItem = amount;
        warnLow = 0;
    }

    public Item(String name)
    {
        itemName = name;
        amountOfItem = 0;
        warnLow = 0;
    }

    public Item()
    {
        itemName = "";
        type = "solid";
        measurement = "none";
        amountOfItem = 0;
        warnLow = 0;
    }

    //Getter Methods
    public String getName()
    {
        return itemName;
    }

    public String getType()
    {
        return type;
    }

    public String getMeasurment()
    {
        return measurement;
    }

    public int getAmount()
    {
        return amountOfItem;
    }

    public int getLow()
    {
        return warnLow;
    }

    //Setter methods
    public void setName(String name)
    {
        itemName = name;
    }

    public void setType(String s)
    {
        type = s;
    }

    public void setMeasurement(String s)
    {
        measurement = s;
    }

    public void setAmount(int newAmount)
    {
        amountOfItem = newAmount;
    }

    public void setLow(int low)
    {
        warnLow = low;
    }

    //our version of an item like toString() ...
    public String getInfo()
    {
        if(amountOfItem != 0)
            return itemName + " " + amountOfItem;
        return itemName;
    }

}
