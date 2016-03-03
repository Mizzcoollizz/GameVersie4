package com.thuis.gameversie2.Inventory_System;

import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Tool;

import java.util.ArrayList;

/**
 * Created by Elize on 20-8-2015.
 */
public class Inventory_Slot_Collection {

    Inventory_Slot[] slots = new Inventory_Slot[20];

    public Inventory_Slot_Collection(){
        for(int i = 0; i < slots.length; i++){
            slots[i] = new Inventory_Slot();
        }
    }

    public boolean addSlot(Item item){
        for(int i = 0; i < slots.length; i++){
            if(slots[i].getAmount() == 0){
                if(item instanceof Tool){
                    slots[i] = new ToolInventorySlot(item);
                }else {
                    slots[i] = new Inventory_Slot(item);
                }
                return true;
            }
        }
        return false;
    }

    public Inventory_Slot[] getSlots() {
        return slots;
    }

    public boolean checkContainsSlotAndAdd(Item item) {
        for(int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].isEqualItem(item) && slots[i].hasRoom()) {
                slots[i].add(item);
                return true;
            }
        }
        return false;
    }
}
