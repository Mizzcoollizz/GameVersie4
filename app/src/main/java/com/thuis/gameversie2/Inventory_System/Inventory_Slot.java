package com.thuis.gameversie2.Inventory_System;

import android.graphics.drawable.Drawable;

import com.thuis.gameversie2.Items.Item;

import java.util.ArrayList;

/**
 * Created by Elize on 20-8-2015.
 */
public class Inventory_Slot {
    ArrayList<Item> items = new ArrayList<>();

    public Inventory_Slot(){

    }

    public Inventory_Slot(Item firstItem) {
        items.add(firstItem);
    }

    public boolean isEqualItem(Item _item) {
        if(items.size() > 0) {
            Item item = items.get(0);
            if (item.isEqualItem(_item)) {
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean hasRoom() {
        return items.size() < 9;
    }

    public void add(Item item) {
        items.add(item);
    }

    public Item getItem() {
        if(items.size() > 0){
            return items.get(0);
        }else{
            return null;
        }
    }

    public int getAmount() {
        if(items.size() > 0){
            return items.size();
        }else{
            return 0;
        }
    }

    public void removeItems() {
        this.items = new ArrayList<>();
    }

    public Item getAndRemoveFirst() {
        Item first = items.get(0);
        items.remove(0);
        return first;
    }

    /**
     * Method for checking if the inventory slot is empty
     * @return true if the the slot is empty
     */
    public boolean isEmpty(){
        return items.isEmpty();
    }
}
