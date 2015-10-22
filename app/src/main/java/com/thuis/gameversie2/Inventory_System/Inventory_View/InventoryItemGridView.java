package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory;
import com.thuis.gameversie2.Inventory_System.Inventory_Slot;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.ItemSelectListener;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnInventoryItemDragListener;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnItemTouchListener;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

import java.util.ArrayList;


public class InventoryItemGridView extends RelativeLayout {

    int position = 0;
    private static final int WIDTH = 72;

    public InventoryItemGridView(Context context, int position, ViewGroup parent) {
        super(context);
        init(context, parent);
        getView(position);
        this.position = position;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public int getPosition() {
        return position;
    }

    private void init(Context context, ViewGroup parent) {
        inflate(getContext(), R.layout.activity_inventory_item_grid_view, this);
        setOnTouchListener(new OnItemTouchListener(parent));

    }

    public void swapItems(InventoryItemGridView item1, InventoryItemGridView item2){
        Inventory_Slot itemInView1 = GamePanel.getInventory().getAllSlots().get(item1.getPosition());
        Inventory_Slot itemInView2 = GamePanel.getInventory().getAllSlots().get(item2.getPosition());

        GamePanel.getInventory().swapItems(itemInView1, itemInView2);
        Log.i("tag", "switched");


    }

    public InventoryItemGridView getView(int position){

        ArrayList<Inventory_Slot> slots = GamePanel.getInventory().getAllSlots();
        ImageView imageView = (ImageView) findViewById(R.id.inventoryItemImageView);
        TextView textViewAmount = (TextView) findViewById(R.id.inventoryAmountTextView);

        try{
            Inventory_Slot inventory_slot = slots.get(position);
            int amount = inventory_slot.getAmount();
            Bitmap image = inventory_slot.getItem().getImage();
            imageView.setImageBitmap(image);
            textViewAmount.setText(Integer.toString(amount));
            return this;

        }catch(NullPointerException ex){
            return this;
        }

    }


    public boolean dropItem(InventoryItemGridView viewDragging, InventoryItemGridView view) {
        try {
            Item item1 = GamePanel.getInventory().getAllSlots().get(viewDragging.getPosition()).getItem();
            Item item2 = GamePanel.getInventory().getAllSlots().get(view.getPosition()).getItem();

            if(item1.isEqualItem(item2)){
                //Todo add multiple items
            }
        }catch(NullPointerException ex){
            view.swapItems(viewDragging, view);
        }


        return false;
    }
}
