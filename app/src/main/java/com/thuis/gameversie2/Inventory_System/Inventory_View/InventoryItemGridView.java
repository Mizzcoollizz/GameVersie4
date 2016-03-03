package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_Slot;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnItemTouchListener;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

import java.util.ArrayList;


public class InventoryItemGridView extends RelativeLayout implements InventoryDropView  {

    private int position = 0;
    private static final int WIDTH = 72;
    private ViewGroup parent = null;

    public InventoryItemGridView(Context context, int position, ViewGroup parent) {
        super(context);
        init(context, parent);
        getView(position);
        this.position = position;
        this.parent = parent;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public int getPosition() {
        return position;
    }

    private void init(Context context, ViewGroup parent) {
        inflate(getContext(), R.layout.activity_inventory_item_grid_view, this);
        setOnTouchListener(new OnItemTouchListener(context, parent));

    }

    public void swapItems(InventoryItemGridView item1, InventoryItemGridView item2){
        Inventory_Slot itemInView1 = GamePanel.getInventory().getAllSlots().get(item1.getPosition());
        Inventory_Slot itemInView2 = GamePanel.getInventory().getAllSlots().get(item2.getPosition());

        GamePanel.getInventory().swapItems(itemInView1, itemInView2);

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

    public boolean dropItem(InventoryDropView _viewDragging, InventoryDropView _view) {
        InventoryItemGridView viewDragging = (InventoryItemGridView) _viewDragging;
        InventoryItemGridView view = (InventoryItemGridView) _view;

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


    @Override
    public boolean checkHasItem() {
        try {
            return !getItemSlot().isEmpty();

        } catch (NullPointerException ex) {
            return false;
        }
    }

    @Override
    public Inventory_Slot getItemSlot(){
        return GamePanel.getInventory().getAllSlots().get(this.getPosition());
    }

    @Override
    public boolean dropItem(Item item, InventoryDropView viewDragging) {
        if(viewDragging instanceof InventoryItemGridView){
            swapItems(this, (InventoryItemGridView) viewDragging);
            return true;
        }else if(viewDragging instanceof InventoryAlternativeDropView){
            InventoryAlternativeDropView inventoryAlternativeDropView = (InventoryAlternativeDropView ) viewDragging;
            Inventory_Slot tempSlot = this.getItemSlot();
            GamePanel.getInventory().putItem(inventoryAlternativeDropView.getItemSlot(), this.getPosition());
            inventoryAlternativeDropView.setItemSlot(tempSlot);
            return true;
        }
        return false;
    }

    @Override
    public Rect getItemImageViewBounds(){
        ImageView itemImageView = (ImageView) findViewById(R.id.inventoryItemImageView);
        return new Rect(0, 0, itemImageView.getWidth(), itemImageView.getHeight());
    }
}
