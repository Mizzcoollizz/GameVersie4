package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.content.ClipData;
import android.view.View;
import android.widget.AdapterView;

import com.thuis.gameversie2.GamePanel;

/**
 * Created by Elize on 1-9-2015.
 */
public class OnInventoryItemLongClickListener implements AdapterView.OnItemLongClickListener {

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            if(!GamePanel.getInventory().getAllSlots().get(position).equals(null)) {
                ClipData data = ClipData.newPlainText((String) view.getTag(),
                        String.valueOf(position));
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                view.startDrag(data, shadow, null, 0);
            }
        }catch(NullPointerException ex){}
        return true;
    }
}
