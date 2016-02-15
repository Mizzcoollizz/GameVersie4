package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory;
import com.thuis.gameversie2.Inventory_System.Inventory_Slot;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnInventoryItemDragListener;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnInventoryItemDragListener2;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnItemTouchListener;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
