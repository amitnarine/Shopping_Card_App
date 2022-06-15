package edu.uga.cs.finalproject;

public class Item {

    private String itemName;

    private double price;

    private String user;
    private Boolean purchased;


    public Item()
    {
        this.itemName = null;
        this.price = 0.00;
        this.purchased = false;

    }

    public Item( String itemName,double price,Boolean purchased, String user) {
        this.itemName = itemName;
        this.price = price;
        this.purchased = false;
        this.user = user;
    }


    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public Boolean getPurchased() {
        return purchased;
    }



    public String toString() {
        return itemName + " " + price + " ";
    }
}
