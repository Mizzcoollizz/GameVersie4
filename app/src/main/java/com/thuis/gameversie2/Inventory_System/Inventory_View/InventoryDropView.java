package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Elize on 2-1-2016.
 */
public interface InventoryDropView {

    boolean dropItem(InventoryDropView viewDragging, InventoryDropView view);
    void onItemSelect();
    boolean checkHasItem();

}
