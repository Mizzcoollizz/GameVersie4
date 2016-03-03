package com.thuis.gameversie2.Inventory_System;

import android.util.Log;

import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;
import com.thuis.gameversie2.Items.Item;

import java.util.ArrayList;

/**
 * Created by Elize on 20-8-2015.
 */
public class Inventory {
    private ArrayList<Inventory_Slot_Collection> currentInventory = new ArrayList<>(20);
    private int level = 1;


    public Inventory(){
        expand();
    }

    public ArrayList<Inventory_Slot> getAllSlots() {
        ArrayList<Inventory_Slot> slots = new ArrayList<>();
        for(Inventory_Slot_Collection inventorySlotsCollection: getCurrentInventory()){
            Inventory_Slot[] inventorySlots = inventorySlotsCollection.getSlots();
            for(int i = 0; i < inventorySlots.length; i++){
                slots.add(inventorySlots[i]);
            }
        }
        return slots;
    }

    public ArrayList<Inventory_Slot_Collection> getCurrentInventory() {
        return currentInventory;
    }

    public int getLevel() {
        return level;
    }

    public boolean add(Item item){
        if(checkAndAddIfItemIsInInventory_Slot(item)){
            return true;
        }else if(createNewItemSlot(item)){
            return true;
        }else{
            return false;
        }
    }

    public boolean remove(Item item){
        return false;
    }

    private boolean createNewItemSlot(Item item){
        boolean created = false;
        for(Inventory_Slot_Collection inventory_slot_collection: getCurrentInventory()){
            created = inventory_slot_collection.addSlot(item);
        }
        return created;
    }

    private void expand(){
        currentInventory.add(new Inventory_Slot_Collection());
    }

    public void levelUP(){
        level++;
        expand();
    }

    private boolean checkAndAddIfItemIsInInventory_Slot(Item item){
        for(Inventory_Slot_Collection inventory_slot: getCurrentInventory()){
            boolean added = inventory_slot.checkContainsSlotAndAdd(item);
            if(added){
                return true;
            }
        }
        return false;
    }

    public void swapItems(Inventory_Slot itemInView1, Inventory_Slot itemInView2) {
        boolean item1Found = false;
        boolean item2Found = false;
        for(Inventory_Slot_Collection inventory_slot_collection: currentInventory){
            for(int i = 0; i < inventory_slot_collection.getSlots().length; i++){
                Inventory_Slot inventory_slot =  inventory_slot_collection.getSlots()[i];
                    if(inventory_slot.equals(itemInView1) && item1Found == false){
                        inventory_slot_collection.getSlots()[i] = itemInView2;
                    }else if(inventory_slot.equals(itemInView2) && item2Found == false){
                        inventory_slot_collection.getSlots()[i] = itemInView1;
                    }
            }
        }
    }

    public void putItem(Inventory_Slot itemSlot, int location){
       int inventory_slot_collection = location / 20;
       int locationInSlotCollection = location % 20;
       currentInventory.get(inventory_slot_collection).getSlots()[locationInSlotCollection] = itemSlot;
    }

    public boolean mergeInventorySlots(InventoryDropView viewDragging, InventoryDropView dropView){
        Inventory_Slot draggingInventorySlot = viewDragging.getItemSlot();
        Inventory_Slot dropViewInventorySlot = dropView.getItemSlot();
        while(dropViewInventorySlot.hasRoom()){
            dropViewInventorySlot.add(draggingInventorySlot.getAndRemoveFirst());
        }
        return true;
    }

}
