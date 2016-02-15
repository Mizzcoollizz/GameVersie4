package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnInventoryItemDragListener;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnInventoryItemDragListener2;
import com.thuis.gameversie2.R;


public class Inventory_Activity extends Activity {

    private static ImageView toolHoldingView = null;
    private static ImageView itemHoldingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GamePanel.setCurrentContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ImageView imageViewPlayer = (ImageView) findViewById(R.id.imageView_Player_Inventory);
        setPlayerImage(imageViewPlayer);
        setPlayerItems();
        View v = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        GridView gridView = (GridView) findViewById(R.id.inventoryGridView);
        View baseView = findViewById(R.id.parentViewInventoryActivity);

        baseView.setOnDragListener(new OnInventoryItemDragListener2((ViewGroup) baseView, this));

        View parentView = findViewById(R.id.parentViewInventoryActivity);
        //parentView.setOnDragListener(new OnInventoryItemDragListener(gridView, (ViewGroup) parentView));
//        v.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                //TODO Working ;)
//                //TODO Just add the next drop grids :/
//
//                return false;
//            }
//        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GamePanel.setCurrentContext(this);
    }

    public static ImageView getToolHoldingView() {
        return toolHoldingView;
    }

    public static ImageView getItemHoldingView() {
        return itemHoldingView;
    }

    private static void setPlayerImage(ImageView imageViewPlayer) {
        Bitmap image = Bitmap.createBitmap(GamePanel.getPlayer().getInventoryImage());
        imageViewPlayer.setImageBitmap(image);
    }

    private void setPlayerItems(){

        itemHoldingView = (ImageView) findViewById(R.id.imageView_player_item_holding);

        itemHoldingView.setImageBitmap(GamePanel.getPlayer().getItemHoldingInventoryImage());

        toolHoldingView = (ImageView) findViewById(R.id.imageViewPlayerToolHolding);

        toolHoldingView.setImageBitmap(GamePanel.getPlayer().getToolHoldingInventoryImage());

    }


}
