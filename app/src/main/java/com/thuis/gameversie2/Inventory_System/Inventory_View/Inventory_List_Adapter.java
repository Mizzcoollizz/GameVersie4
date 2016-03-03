package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thuis.gameversie2.GamePanel;

/**
 * Created by Elize on 24-8-2015.
 */
public class Inventory_List_Adapter extends BaseAdapter {

    Context context = null;
    private static LayoutInflater inflater = null;

    public Inventory_List_Adapter(Context context){
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return GamePanel.getInventory().getAllSlots().size();
    }

    @Override
    public Object getItem(int position) {
       return GamePanel.getInventory().getAllSlots().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InventoryItemGridView returnView = new InventoryItemGridView(this.context, position, parent);
        return returnView;
    }
}
