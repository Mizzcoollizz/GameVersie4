package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import com.thuis.gameversie2.Inventory_System.Inventory_Slot;
import com.thuis.gameversie2.Items.Item;

/**
 * Created by Elize on 2-1-2016.
 */
public interface InventoryDropView {

    boolean dropItem(InventoryDropView viewDragging, InventoryDropView view);
    boolean checkHasItem();
    Inventory_Slot getItemSlot();
    boolean dropItem(Item item, InventoryDropView dropView);
    Rect getItemImageViewBounds();

    
}
