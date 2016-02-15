package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public void onItemSelect() {

            View activity = parent.getRootView();
            int position = this.getPosition();
            TextView nameTextView = (TextView) activity.findViewById(R.id.nameTextView_Inventory_input);
            TextView typeTextView = (TextView) activity.findViewById(R.id.typeTextView_Inventory_input);
            TextView gradeTextView = (TextView) activity.findViewById(R.id.gradeTextView_Inventory_input);
            ImageView imageView = (ImageView) activity.findViewById(R.id.ImageView_Selected_Item);
            try {
                //When the user clicks on an item, the item background will be green.
                //The other elements have a transparent background.
                for(int i = 0; i < parent.getChildCount(); i++){
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                this.setBackgroundColor(Color.RED);

                Item item = GamePanel.getInventory().getAllSlots().get(position).getItem();

                if (item != null && !item.equals(null) && this != null) {
                    nameTextView.setText(item.getName());
                    typeTextView.setText(item.getClass().getSimpleName());
                    gradeTextView.setText(Integer.toString(item.getGrade()));

                    Bitmap imageBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.inventory_item_border);
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 92, 92, false);

                    imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_4444, true);
                    Canvas canvas = new Canvas(imageBitmap);
                    Bitmap itemImage = Bitmap.createScaledBitmap(item.getImage(), 92, 92, false);
                    canvas.drawBitmap(itemImage, 0, 0, null);
                    imageView.setImageBitmap(imageBitmap);
                }else{
                    throw new NullPointerException();
                }

            }catch (NullPointerException ex){
                ex.printStackTrace();
                nameTextView.setText(null);
                typeTextView.setText(null);
                gradeTextView.setText(null);
                Bitmap imageBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.inventory_item_border);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 92, 92, false);
                imageView.setImageBitmap(imageBitmap);
            }


    }

    @Override
    public boolean checkHasItem() {
        try {
            if (GamePanel.getInventory().getAllSlots().get(this.getPosition()).getAmount() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (NullPointerException ex) {
            return false;
        }
    }
}
