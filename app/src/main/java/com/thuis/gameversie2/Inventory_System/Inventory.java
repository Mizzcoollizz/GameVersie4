package com.thuis.gameversie2.Inventory_System;

import android.util.Log;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;
import com.thuis.gameversie2.Items.Item;

import java.util.ArrayList;
import java.util.Collections;

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
            Collections.addAll(slots, inventorySlots);
        }
        return slots;
    }

    public ArrayList<Inventory_Slot_Collection> getCurrentInventory() {
        return currentInventory;
    }

    public int getLevel() {
        return level;
    }

    public boolean add(Item item) {
        return checkAndAddIfItemIsInInventory_Slot(item) || createNewItemSlot(item);
    }

    public boolean addItemSlot(Inventory_Slot slotToAdd) {

        for (Inventory_Slot slot : getAllSlots()) {
            if (slot.isEqualItem(slotToAdd.getItem()) && slot.hasRoom()) {
                mergeInventorySlots(slotToAdd, slot);
                break;
            }
        }

        return slotToAdd.isEmpty() || addNewInventorySlot(slotToAdd);

    }

    private boolean addNewInventorySlot(Inventory_Slot slotToAdd){
        ArrayList<Inventory_Slot> allSlots = getAllSlots();
        for(int i = 0; i < allSlots.size(); i++){
            Inventory_Slot slot = allSlots.get(i);
            if(slot.isEmpty()){
                GamePanel.getInventory().putItem(slotToAdd, i);
                return true;
            }
        }
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
        ArrayList<Inventory_Slot> allSlots = getAllSlots();
        for(int i = 0; i < allSlots.size(); i++){
            Inventory_Slot slot = allSlots.get(i);
            if(slot.isEqualItem(item) && slot.hasRoom()){
                slot.add(item);
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
                    if(inventory_slot.equals(itemInView1) && !item1Found){
                        inventory_slot_collection.getSlots()[i] = itemInView2;
                        item1Found = true;
                    }else if(inventory_slot.equals(itemInView2) && !item2Found){
                        inventory_slot_collection.getSlots()[i] = itemInView1;
                        item2Found = true;
                    }
            }
        }
    }

    public void putItem(Inventory_Slot itemSlot, int location){
       int inventory_slot_collection = location / 20;
       int locationInSlotCollection = location % 20;
       currentInventory.get(inventory_slot_collection).getSlots()[locationInSlotCollection] = itemSlot;
    }

    public boolean mergeInventorySlots(Inventory_Slot draggingInventorySlot, Inventory_Slot dropInventorySlot){

        while(dropInventorySlot.hasRoom() && !draggingInventorySlot.isEmpty()){
            dropInventorySlot.add(draggingInventorySlot.getAndRemoveFirst());
        }
        return true;
    }

    public boolean createNewSlotIfPossible(Item item){
        int i = getFirstEmptySlotPosition();
        if(i != -1){
            Inventory_Slot new_inventory_slot = new Inventory_Slot(item);
            putItem(new_inventory_slot, i);
            return true;
        }
        return false;
    }

    private int getFirstEmptySlotPosition(){
        ArrayList<Inventory_Slot> allSlots = getAllSlots();
        for(int i = 0; i < allSlots.size(); i++){
            Inventory_Slot inventory_slot = allSlots.get(i);
            if(inventory_slot.isEmpty()){
                return i;
            }
        }
        return -1;
    }


}
